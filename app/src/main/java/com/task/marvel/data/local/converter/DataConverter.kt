package com.task.marvel.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.task.marvel.data.dtos.responsedtos.characters.Character
import com.task.marvel.data.dtos.responsedtos.comics.Comic

class DataConverter {
    @TypeConverter
    fun fromCharacterList(topics: List<Character?>?): String? {
        if (topics == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Character?>?>() {}.type
        return gson.toJson(topics, type)
    }

    @TypeConverter
    fun toCharacterList(topics: String?): List<Character?>? {
        if (topics == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Character?>?>() {}.type
        return gson.fromJson(topics, type)
    }

    @TypeConverter
    fun fromComicList(topics: List<Comic?>?): String? {
        if (topics == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Comic?>?>() {}.type
        return gson.toJson(topics, type)
    }

    @TypeConverter
    fun toComicList(topics: String?): List<Comic?>? {
        if (topics == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Comic?>?>() {}.type
        return gson.fromJson(topics, type)
    }
}