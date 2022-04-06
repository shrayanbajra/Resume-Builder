package com.example.resumebuilder.data.dao

import androidx.room.*
import com.example.resumebuilder.data.entities.Resume
import kotlinx.coroutines.flow.Flow

@Dao
interface ResumeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(resume: Resume): Long

    @Query("SELECT * FROM resume_table WHERE id = :id")
    suspend fun get(id: Long): Resume

    @Query("SELECT * FROM resume_table ORDER BY id ASC")
    fun getAll(): Flow<List<Resume>>

    @Delete
    suspend fun delete(resume: Resume): Int

    @Query("DELETE FROM resume_table")
    suspend fun deleteAll()

}