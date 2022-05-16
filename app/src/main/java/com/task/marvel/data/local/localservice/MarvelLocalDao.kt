package com.task.marvel.data.local.localservice

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.task.marvel.data.dtos.responsedtos.characters.CharacterData
import com.task.marvel.data.dtos.responsedtos.comics.ComicData

@Dao
interface MarvelLocalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(characterData: CharacterData)

    @Query("SELECT * FROM character_marvel Where `offset` == :offset")
    suspend fun getCharacter(offset: Int): CharacterData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComic(comicData: ComicData)

    @Query("SELECT * FROM comic_marvel Where `offset` == :offset")
    suspend fun getComic(offset: Int): ComicData?
}