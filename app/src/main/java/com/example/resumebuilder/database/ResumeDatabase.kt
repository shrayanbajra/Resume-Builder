package com.example.resumebuilder.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ResumeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ResumeDatabase : RoomDatabase() {

    abstract fun resumeDao(): ResumeDao

}