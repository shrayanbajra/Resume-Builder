package com.example.resumebuilder.repository

import com.example.resumebuilder.data.entities.Resume
import kotlinx.coroutines.flow.Flow

interface ResumeRepository {

    suspend fun saveResumeToDatabase(resume: Resume): Long

    suspend fun getResumeFromDatabase(id: Long): Resume

    fun getAllResumesFromDatabase(): Flow<List<Resume>>

    suspend fun deleteResumeFromDatabase(resume: Resume): Int

}