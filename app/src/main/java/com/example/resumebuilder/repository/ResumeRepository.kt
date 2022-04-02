package com.example.resumebuilder.repository

import com.example.resumebuilder.data.Resume

interface ResumeRepository {

    suspend fun saveResumeToDatabase(resume: Resume): Long

    suspend fun getResumeFromDatabase(id: Long): Resume

}