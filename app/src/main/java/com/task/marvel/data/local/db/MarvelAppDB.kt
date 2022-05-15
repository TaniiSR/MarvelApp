package com.task.marvel.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.task.marvel.data.dtos.responsedtos.CharacterData
import com.task.marvel.data.local.converter.DataConverter
import com.task.marvel.data.local.localservice.MarvelLocalDao

@Database(entities = [CharacterData::class], version = 1, exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class MarvelAppDB : RoomDatabase() {
    abstract fun MarvelLocalDao(): MarvelLocalDao
}