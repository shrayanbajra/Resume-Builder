package com.example.resumebuilder.di

import android.content.Context
import androidx.room.Room
import com.example.resumebuilder.data.dao.ResumeDao
import com.example.resumebuilder.data.database.ResumeDatabase
import com.example.resumebuilder.repository.ResumeRepository
import com.example.resumebuilder.repository.ResumeRepositoryImpl
import com.example.resumebuilder.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(context, ResumeDatabase::class.java, Constants.RESUME_DATABASE).build()

    @Singleton
    @Provides
    fun provideResumeDao(database: ResumeDatabase) = database.resumeDao()

    @Singleton
    @Provides
    fun provideResumeRepository(resumeDao: ResumeDao): ResumeRepository {
        return ResumeRepositoryImpl(resumeDao)
    }

}