package com.example.resumebuilder.data

import androidx.room.*
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
    suspend fun delete(resume: Resume)

    @Query("DELETE FROM resume_table")
    suspend fun deleteAll()

}