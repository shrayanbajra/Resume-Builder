package com.example.resumebuilder.repository

import com.example.resumebuilder.data.Resume
import com.example.resumebuilder.data.ResumeDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ResumeRepositoryImpl
@Inject
constructor(private val resumeDao: ResumeDao) : ResumeRepository {

    override suspend fun saveResumeToDatabase(resume: Resume): Long {

        return withContext(Dispatchers.IO) {

            return@withContext resumeDao.insert(resume = resume)

        }

    }

    override suspend fun getResumeFromDatabase(id: Long): Resume {

        return withContext(Dispatchers.IO) {

            return@withContext resumeDao.get(id = id)

        }

    }
}