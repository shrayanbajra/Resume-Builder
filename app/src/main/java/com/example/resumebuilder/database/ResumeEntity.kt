package com.example.resumebuilder.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.resumebuilder.utils.Constants

@Entity(tableName = Constants.RESUME_TABLE)
data class ResumeEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private val id: Int,

    @ColumnInfo(name = "image_uri")
    private val imageUri: String

)