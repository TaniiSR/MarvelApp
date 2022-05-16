package com.task.marvel.data.local.localservice

import com.task.marvel.data.dtos.responsedtos.characters.CharacterData
import com.task.marvel.data.dtos.responsedtos.comics.ComicData


interface MarvelRepoDbService {
    suspend fun insertCharacter(characterData: CharacterData)
    suspend fun getCharacter(offset: Int): CharacterData?

    suspend fun insertComic(comicData: ComicData)
    suspend fun getComic(offset: Int): ComicData?
}