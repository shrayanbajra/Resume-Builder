package com.example.resumebuilder.repository

import com.example.resumebuilder.database.ResumeEntity

interface ResumeRepository {

    suspend fun insertResume(resumeEntity: ResumeEntity)

}