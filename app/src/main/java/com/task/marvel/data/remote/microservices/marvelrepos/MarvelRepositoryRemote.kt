package com.task.marvel.data.remote.microservices.marvelrepos


import com.task.marvel.data.dtos.responsedtos.CharactersResponse
import com.task.marvel.data.remote.baseclient.ApiResponse
import com.task.marvel.data.remote.baseclient.BaseRepository
import javax.inject.Inject

class MarvelRepositoryRemote @Inject constructor(
    private val service: MarvelRepoRetroService
) : BaseRepository(), MarvelRepoApi {

    companion object {
        const val URL_GET_CHARACTERS = "v1/public/characters"
    }

    override suspend fun getCharacters(
        apiKey: String,
        hash: String,
        ts: String,
        offset: Int
    ): ApiResponse<CharactersResponse> {
        return executeSafely(call = {
            service.getCharacters(
                apikey = apiKey,
                hash = hash,
                ts = ts,
                offset = offset
            )
        })
    }
}