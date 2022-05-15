package com.task.marvel.domain

import com.task.marvel.data.dtos.responsedtos.CharacterData
import com.task.marvel.data.local.localservice.MarvelRepoDbService
import com.task.marvel.data.remote.baseclient.ApiResponse
import com.task.marvel.data.remote.baseclient.erros.ApiError
import com.task.marvel.data.remote.microservices.marvelrepos.MarvelRepoApi
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val remoteRepository: MarvelRepoApi,
    private val localRepository: MarvelRepoDbService
) : IDataRepository {
    override suspend fun getMarvelCharacter(
        apiKey: String,
        hash: String,
        ts: String,
        offset: Int,
    ): ApiResponse<CharacterData> {
        val repos = localRepository.getCharacter(offset)
        return when {
            repos != null -> {
                ApiResponse.Success(
                    200,
                    data = repos
                )
            }
            else -> {
                val response = remoteRepository.getCharacters(
                    apiKey = apiKey,
                    offset = offset,
                    hash = hash,
                    ts = ts
                )
                return if (response is ApiResponse.Success) {
                    response.data.data?.let { localRepository.insertCharacter(it) }
                    ApiResponse.Success(
                        200,
                        data = response.data.data ?: CharacterData()
                    )
                } else ApiResponse.Error(ApiError(100, "Api Failed"))
            }
        }
    }
}