package com.example.resumebuilder.repository

import com.example.resumebuilder.database.ResumeDao
import com.example.resumebuilder.database.ResumeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ResumeRepositoryImpl
@Inject
constructor(private val resumeDao: ResumeDao) : ResumeRepository {

    override suspend fun insertResume(resumeEntity: ResumeEntity) {

        withContext(Dispatchers.IO) {

            resumeDao.insert(resume = resumeEntity)

        }

    }

}