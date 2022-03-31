package com.example.resumebuilder.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.resumebuilder.utils.Constants

@Entity(tableName = Constants.RESUME_TABLE)
data class ResumeEntity(

    @ColumnInfo(name = "image_uri")
    val imageUri: String

) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = Constants.NO_PRIMARY_KEY

}