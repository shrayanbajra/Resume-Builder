package com.example.resumebuilder.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.resumebuilder.data.entities.Resume
import com.example.resumebuilder.data.entities.ResumeDao
import com.example.resumebuilder.data.type_converters.EducationDetailsTypeConverter
import com.example.resumebuilder.data.type_converters.ProjectTypeConverter
import com.example.resumebuilder.data.type_converters.SkillsTypeConverter
import com.example.resumebuilder.data.type_converters.WorkDetailsTypeConverter

@Database(entities = [Resume::class], version = 1, exportSchema = false)
@TypeConverters(
    WorkDetailsTypeConverter::class,
    SkillsTypeConverter::class,
    EducationDetailsTypeConverter::class,
    ProjectTypeConverter::class
)
abstract class ResumeDatabase : RoomDatabase() {

    abstract fun resumeDao(): ResumeDao

}