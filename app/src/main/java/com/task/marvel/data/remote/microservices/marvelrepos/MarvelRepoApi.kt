package com.task.marvel.data.remote.microservices.marvelrepos

import com.task.marvel.data.dtos.responsedtos.CharactersResponse
import com.task.marvel.data.remote.baseclient.ApiResponse


interface MarvelRepoApi {
    suspend fun getCharacters(
        apiKey: String,
        hash: String,
        ts: String,
        offset: Int,
    ): ApiResponse<CharactersResponse>

}