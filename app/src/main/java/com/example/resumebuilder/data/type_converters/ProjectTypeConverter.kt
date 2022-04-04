package com.example.resumebuilder.data.type_converters

import androidx.room.TypeConverter
import com.example.resumebuilder.data.entities.Project
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class ProjectTypeConverter {

    @TypeConverter
    fun jsonStringToProjects(data: String?): List<Project?>? {

        val gson = Gson()
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<Project?>?>() {}.type
        return gson.fromJson(data, listType)

    }

    @TypeConverter
    fun projectsToJsonString(someObjects: List<Project?>?): String? {

        val gson = Gson()
        return gson.toJson(someObjects)

    }

}