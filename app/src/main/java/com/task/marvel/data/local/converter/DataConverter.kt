package com.task.marvel.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.task.marvel.data.dtos.responsedtos.Character

class DataConverter {
    @TypeConverter
    fun fromList(topics: List<Character?>?): String? {
        if (topics == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Character?>?>() {}.type
        return gson.toJson(topics, type)
    }

    @TypeConverter
    fun toList(topics: String?): List<Character?>? {
        if (topics == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Character?>?>() {}.type
        return gson.fromJson(topics, type)
    }
}