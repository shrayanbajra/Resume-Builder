package com.example.resumebuilder.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.resumebuilder.data.Resume
import com.example.resumebuilder.data.ResumeDao

@Database(
    entities = [Resume::class],
    version = 1,
    exportSchema = false
)
abstract class ResumeDatabase : RoomDatabase() {

    abstract fun resumeDao(): ResumeDao

}