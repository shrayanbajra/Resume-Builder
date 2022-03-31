package com.example.resumebuilder.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ResumeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(resume: ResumeEntity): Long

    @Query("SELECT * FROM resume_table WHERE id = :id")
    suspend fun get(id: Long): ResumeEntity

    @Query("SELECT * FROM resume_table ORDER BY id ASC")
    fun getAll(): Flow<List<ResumeEntity>>

    @Delete
    suspend fun delete(resume: ResumeEntity)

    @Query("DELETE FROM resume_table")
    suspend fun deleteAll()

}