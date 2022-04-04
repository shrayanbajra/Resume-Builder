package com.example.resumebuilder.data.type_converters

import androidx.room.TypeConverter
import com.example.resumebuilder.data.entities.EducationDetails
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class EducationDetailsTypeConverter {

    @TypeConverter
    fun jsonStringToEducationDetails(data: String?): List<EducationDetails?>? {

        val gson = Gson()
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<EducationDetails?>?>() {}.type
        return gson.fromJson(data, listType)

    }

    @TypeConverter
    fun educationDetailsToJsonString(someObjects: List<EducationDetails?>?): String? {

        val gson = Gson()
        return gson.toJson(someObjects)

    }

}