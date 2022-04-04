package com.example.resumebuilder.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.resumebuilder.data.Resume
import com.example.resumebuilder.data.ResumeDao
import com.example.resumebuilder.data.type_converters.SkillsTypeConverter
import com.example.resumebuilder.data.type_converters.WorkDetailsTypeConverter

@Database(entities = [Resume::class], version = 1, exportSchema = false)
@TypeConverters(WorkDetailsTypeConverter::class, SkillsTypeConverter::class)
abstract class ResumeDatabase : RoomDatabase() {

    abstract fun resumeDao(): ResumeDao

}