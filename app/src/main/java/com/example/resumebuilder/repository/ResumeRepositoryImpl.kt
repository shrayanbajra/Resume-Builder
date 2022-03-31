package com.example.resumebuilder.repository

import com.example.resumebuilder.database.ResumeDao
import com.example.resumebuilder.database.ResumeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ResumeRepositoryImpl
@Inject
constructor(private val resumeDao: ResumeDao) : ResumeRepository {

    override suspend fun saveResumeToDatabase(resumeEntity: ResumeEntity): Long {

        return withContext(Dispatchers.IO) {

            return@withContext resumeDao.insert(resume = resumeEntity)

        }

    }

    override suspend fun getResumeFromDatabase(id: Long): ResumeEntity {

        return withContext(Dispatchers.IO) {

            return@withContext resumeDao.get(id = id)

        }

    }
}