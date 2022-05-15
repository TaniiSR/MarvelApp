package com.task.marvel.domain

import com.task.marvel.data.dtos.responsedtos.CharacterData
import com.task.marvel.data.remote.baseclient.ApiResponse


interface IDataRepository {
    suspend fun getMarvelCharacter(
        apiKey: String,
        hash: String,
        ts: String,
        offset: Int,
    ): ApiResponse<CharacterData>

}