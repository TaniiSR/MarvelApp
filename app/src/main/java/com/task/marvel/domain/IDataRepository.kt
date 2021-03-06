package com.task.marvel.domain

import com.task.marvel.data.dtos.responsedtos.characters.CharacterData
import com.task.marvel.data.dtos.responsedtos.comics.ComicData
import com.task.marvel.data.remote.baseclient.ApiResponse


interface IDataRepository {
    suspend fun getMarvelCharacter(
        apiKey: String,
        hash: String,
        ts: String,
        offset: Int,
    ): ApiResponse<CharacterData>

    suspend fun getMarvelComics(
        apiKey: String,
        hash: String,
        ts: String,
        offset: Int,
    ): ApiResponse<ComicData>

}