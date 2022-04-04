package com.example.resumebuilder.repository

import com.example.resumebuilder.data.entities.Resume

interface ResumeRepository {

    suspend fun saveResumeToDatabase(resume: Resume): Long

    suspend fun getResumeFromDatabase(id: Long): Resume

    suspend fun getAllResumesFromDatabase(): List<Resume>

}