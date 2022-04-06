package com.example.resumebuilder.di

import com.example.resumebuilder.repository.ResumeRepository
import com.example.resumebuilder.repository.ResumeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideResumeRepository(repository: ResumeRepositoryImpl): ResumeRepository

}