package com.task.marvel.data.local.localservice

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.task.marvel.data.dtos.responsedtos.CharacterData

@Dao
interface MarvelLocalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(characterData: CharacterData)

    @Query("SELECT * FROM character_marvel Where `offset` == :offset")
    suspend fun getCharacter(offset: Int): CharacterData?
}