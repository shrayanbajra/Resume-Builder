package com.example.resumebuilder.repository

import com.example.resumebuilder.database.ResumeEntity

interface ResumeRepository {

    suspend fun saveResumeToDatabase(resumeEntity: ResumeEntity): Long

    suspend fun getResumeFromDatabase(id: Long): ResumeEntity

}