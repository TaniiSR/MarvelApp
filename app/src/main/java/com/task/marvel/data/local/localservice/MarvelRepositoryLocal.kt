package com.task.marvel.data.local.localservice

import com.task.marvel.data.dtos.responsedtos.characters.CharacterData
import com.task.marvel.data.dtos.responsedtos.comics.ComicData
import javax.inject.Inject

class MarvelRepositoryLocal @Inject constructor(private val marvelLocalDao: MarvelLocalDao) :
    MarvelRepoDbService {
    override suspend fun insertCharacter(characterData: CharacterData) {
        marvelLocalDao.insertCharacter(characterData)
    }

    override suspend fun getCharacter(offset: Int): CharacterData? =
        marvelLocalDao.getCharacter(offset)

    override suspend fun insertComic(comicData: ComicData) {
        marvelLocalDao.insertComic(comicData)
    }

    override suspend fun getComic(offset: Int): ComicData? =
        marvelLocalDao.getComic(offset)

}