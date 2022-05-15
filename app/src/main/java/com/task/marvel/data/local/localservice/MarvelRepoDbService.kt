package com.task.marvel.data.local.localservice

import com.task.marvel.data.dtos.responsedtos.CharacterData


interface MarvelRepoDbService {
    suspend fun insertCharacter(characterData: CharacterData)
    suspend fun getCharacter(offset: Int): CharacterData?
}