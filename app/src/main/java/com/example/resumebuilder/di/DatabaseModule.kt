package com.example.resumebuilder.di

import android.content.Context
import androidx.room.Room
import com.example.resumebuilder.database.ResumeDatabase
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
    ) = Room.databaseBuilder(
        context,
        ResumeDatabase::class.java,
        Constants.RESUME_DATABASE
    ).build()

    @Singleton
    @Provides
    fun provideResumeDao(database: ResumeDatabase) = database.resumeDao()

}