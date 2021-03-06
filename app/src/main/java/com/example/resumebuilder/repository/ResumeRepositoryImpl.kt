package com.example.resumebuilder.repository

import com.example.resumebuilder.data.dao.ResumeDao
import com.example.resumebuilder.data.entities.Resume
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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

    override fun getAllResumesFromDatabase(): Flow<List<Resume>> {

        return resumeDao.getAll()

    }

    override suspend fun deleteResumeFromDatabase(resume: Resume): Int {

        return withContext(Dispatchers.IO) {

            return@withContext resumeDao.delete(resume)

        }

    }

}