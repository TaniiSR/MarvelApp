package com.task.marvel.data.remote.microservices.marvelrepos


import com.task.marvel.data.dtos.responsedtos.characters.CharactersResponse
import com.task.marvel.data.dtos.responsedtos.comics.ComicResponse
import com.task.marvel.data.remote.baseclient.ApiResponse
import com.task.marvel.data.remote.baseclient.BaseRepository
import javax.inject.Inject

class MarvelRepositoryRemote @Inject constructor(
    private val service: MarvelRepoRetroService
) : BaseRepository(), MarvelRepoApi {

    companion object {
        const val URL_GET_CHARACTERS = "v1/public/characters"
        const val URL_GET_COMICS = "v1/public/comics"
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

    override suspend fun getComics(
        apiKey: String,
        hash: String,
        ts: String,
        offset: Int
    ): ApiResponse<ComicResponse> {
        return executeSafely(call = {
            service.getComics(
                apikey = apiKey,
                hash = hash,
                ts = ts,
                offset = offset
            )
        })
    }
}