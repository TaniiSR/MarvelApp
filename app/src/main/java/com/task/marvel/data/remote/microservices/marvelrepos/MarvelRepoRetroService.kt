package com.task.marvel.data.remote.microservices.marvelrepos


import com.task.marvel.data.dtos.responsedtos.characters.CharactersResponse
import com.task.marvel.data.dtos.responsedtos.comics.ComicResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelRepoRetroService {
    //Get the characters
    @GET(MarvelRepositoryRemote.URL_GET_CHARACTERS)
    suspend fun getCharacters(
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("ts") ts: String,
        @Query("offset") offset: Int,
    ): Response<CharactersResponse>

    @GET(MarvelRepositoryRemote.URL_GET_COMICS)
    suspend fun getComics(
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("ts") ts: String,
        @Query("offset") offset: Int,
    ): Response<ComicResponse>
}