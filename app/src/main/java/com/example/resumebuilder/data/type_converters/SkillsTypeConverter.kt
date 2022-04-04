package com.example.resumebuilder.data.type_converters

import androidx.room.TypeConverter
import com.example.resumebuilder.data.entities.Skill
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class SkillsTypeConverter {

    @TypeConverter
    fun jsonStringToSkills(data: String?): List<Skill?>? {

        val gson = Gson()
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<Skill?>?>() {}.type
        return gson.fromJson(data, listType)

    }

    @TypeConverter
    fun skillsToJsonString(someObjects: List<Skill?>?): String? {

        val gson = Gson()
        return gson.toJson(someObjects)

    }

}