package com.task.marvel.data.local.localservice

import com.task.marvel.data.dtos.responsedtos.CharacterData
import javax.inject.Inject

class MarvelRepositoryLocal @Inject constructor(private val marvelLocalDao: MarvelLocalDao) :
    MarvelRepoDbService {
    override suspend fun insertCharacter(characterData: CharacterData) {
        marvelLocalDao.insertCharacter(characterData)
    }

    override suspend fun getCharacter(offset: Int): CharacterData? =
        marvelLocalDao.getCharacter(offset)
}