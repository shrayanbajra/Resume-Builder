package com.example.resumebuilder.data.type_converters

import androidx.room.TypeConverter
import com.example.resumebuilder.data.WorkDetails
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class WorkDetailsTypeConverter {

    @TypeConverter
    fun jsonStringToWorkDetails(data: String?): List<WorkDetails?>? {

        val gson = Gson()
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<WorkDetails?>?>() {}.type
        return gson.fromJson(data, listType)

    }

    @TypeConverter
    fun workDetailsToJsonString(someObjects: List<WorkDetails?>?): String? {

        val gson = Gson()
        return gson.toJson(someObjects)

    }

}