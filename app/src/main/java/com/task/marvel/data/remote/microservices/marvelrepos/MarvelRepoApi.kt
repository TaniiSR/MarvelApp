package com.task.marvel.data.remote.microservices.marvelrepos

import com.task.marvel.data.dtos.responsedtos.characters.CharactersResponse
import com.task.marvel.data.dtos.responsedtos.comics.ComicResponse
import com.task.marvel.data.remote.baseclient.ApiResponse


interface MarvelRepoApi {
    suspend fun getCharacters(
        apiKey: String,
        hash: String,
        ts: String,
        offset: Int,
    ): ApiResponse<CharactersResponse>

    suspend fun getComics(
        apiKey: String,
        hash: String,
        ts: String,
        offset: Int,
    ): ApiResponse<ComicResponse>

}