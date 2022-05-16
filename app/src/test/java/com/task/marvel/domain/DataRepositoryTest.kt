package com.task.marvel.domain

import com.task.marvel.base.BaseTestCase
import com.task.marvel.data.dtos.responsedtos.characters.CharacterData
import com.task.marvel.data.dtos.responsedtos.characters.CharactersResponse
import com.task.marvel.data.dtos.responsedtos.comics.ComicData
import com.task.marvel.data.dtos.responsedtos.comics.ComicResponse
import com.task.marvel.data.local.localservice.MarvelRepoDbService
import com.task.marvel.data.local.localservice.MarvelRepositoryLocal
import com.task.marvel.data.remote.baseclient.ApiResponse
import com.task.marvel.data.remote.microservices.marvelrepos.MarvelRepoApi
import com.task.marvel.data.remote.microservices.marvelrepos.MarvelRepositoryRemote
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DataRepositoryTest : BaseTestCase() {
    // Subject under test
    lateinit var dataRepository: DataRepository

    // Use a fake UseCase to be injected into the DataRepository
    lateinit var remoteData: MarvelRepoApi
    lateinit var localData: MarvelRepoDbService

    @Before
    fun setUp() {
        remoteData = mockk<MarvelRepositoryRemote>()
        localData = mockk<MarvelRepositoryLocal>()
    }

    @Test
    fun `get character local success`() {
        //1- Mock calls
        val key = ""
        val hash = ""
        val time = "Sun, 15 May 2022 17:00:01 GMT"
        val offset = 0
        runTest {
            val response = mockk<ApiResponse.Success<CharacterData>> {
                every { data } returns mockk {
                    every { results } returns listOf()
                    every { timeStamp } returns time
                }
            }
            val character = response.data

            coEvery {
                localData.getCharacter(offset)
            } returns character

            //2-Call
            dataRepository = DataRepository(remoteData, localData)
            val actual: ApiResponse<CharacterData> = dataRepository.getMarvelCharacter(
                key,
                hash,
                time,
                offset
            )
            //3-verify
            Assert.assertEquals(response.data, (actual as ApiResponse.Success).data)

        }
    }

    @Test
    fun `get comic local success`() {
        //1- Mock calls
        val key = ""
        val hash = ""
        val time = "Sun, 15 May 2022 17:00:01 GMT"
        val offset = 0
        runTest {
            val response = mockk<ApiResponse.Success<ComicData>> {
                every { data } returns mockk {
                    every { results } returns listOf()
                    every { timeStamp } returns time
                }
            }
            val comicData = response.data

            coEvery {
                localData.getComic(offset)
            } returns comicData

            //2-Call
            dataRepository = DataRepository(remoteData, localData)
            val actual: ApiResponse<ComicData> = dataRepository.getMarvelComics(
                key,
                hash,
                time,
                offset
            )
            //3-verify
            Assert.assertEquals(response.data, (actual as ApiResponse.Success).data)

        }
    }

    @Test
    fun `get character remote with no local success`() {
        //1- Mock calls
        val key = ""
        val hash = ""
        val time = "Sun, 15 May 2022 17:00:01 GMT"
        val offset = 0
        runTest {
            val response = mockk<ApiResponse.Success<CharactersResponse>> {
                every { data } returns mockk {
                    every { data } returns mockk(relaxed = true)
                }
            }
            val character = response.data.data ?: CharacterData()
            coEvery {
                localData.insertCharacter(character)
            } returns Unit

            coEvery {
                localData.getCharacter(offset)
            } returns null

            coEvery {
                remoteData.getCharacters(
                    key,
                    hash,
                    time,
                    offset
                )
            } returns response
            //2-Call
            dataRepository = DataRepository(remoteData, localData)
            val actual: ApiResponse<CharacterData> = dataRepository.getMarvelCharacter(
                key,
                hash,
                time,
                offset
            )
            //3-verify
            Assert.assertEquals(response.data.data, (actual as ApiResponse.Success).data)

        }
    }

    @Test
    fun `get comic remote with no local success`() {
        //1- Mock calls
        val key = ""
        val hash = ""
        val time = "Sun, 15 May 2022 17:00:01 GMT"
        val offset = 0
        runTest {
            val response = mockk<ApiResponse.Success<ComicResponse>> {
                every { data } returns mockk {
                    every { data } returns mockk(relaxed = true)
                }
            }
            val comicData = response.data.data ?: ComicData()
            coEvery {
                localData.insertComic(comicData)
            } returns Unit

            coEvery {
                localData.getComic(offset)
            } returns null

            coEvery {
                remoteData.getComics(
                    key,
                    hash,
                    time,
                    offset
                )
            } returns response
            //2-Call
            dataRepository = DataRepository(remoteData, localData)
            val actual: ApiResponse<ComicData> = dataRepository.getMarvelComics(
                key,
                hash,
                time,
                offset
            )
            //3-verify
            Assert.assertEquals(response.data.data, (actual as ApiResponse.Success).data)

        }
    }

    @Test
    fun `get character remote with expire cache success`() {
        //1- Mock calls
        val key = ""
        val hash = ""
        val time = "Mon, 16 May 2022 17:00:01 GMT"
        val offset = 0
        runTest {
            val response = mockk<ApiResponse.Success<CharactersResponse>> {
                every { data } returns mockk {
                    every { data } returns mockk(relaxed = true)
                }
            }
            val character = response.data.data ?: CharacterData()
            character.timeStamp = "Sat, 14 May 2022 17:00:01 GMT"
            coEvery {
                localData.insertCharacter(character)
            } returns Unit

            coEvery {
                localData.getCharacter(offset)
            } returns character

            coEvery {
                remoteData.getCharacters(
                    key,
                    hash,
                    time,
                    offset
                )
            } returns response
            //2-Call
            dataRepository = DataRepository(remoteData, localData)
            val actual: ApiResponse<CharacterData> = dataRepository.getMarvelCharacter(
                key,
                hash,
                time,
                offset
            )
            //3-verify
            Assert.assertEquals(response.data.data, (actual as ApiResponse.Success).data)

        }
    }

    @Test
    fun `get comic remote with expire cache success`() {
        //1- Mock calls
        val key = ""
        val hash = ""
        val time = "Mon, 16 May 2022 17:00:01 GMT"
        val offset = 0
        runTest {
            val response = mockk<ApiResponse.Success<ComicResponse>> {
                every { data } returns mockk {
                    every { data } returns mockk(relaxed = true)
                }
            }
            val comicData = response.data.data ?: ComicData()
            comicData.timeStamp = "Sat, 14 May 2022 17:00:01 GMT"
            coEvery {
                localData.insertComic(comicData)
            } returns Unit

            coEvery {
                localData.getComic(offset)
            } returns comicData

            coEvery {
                remoteData.getComics(
                    key,
                    hash,
                    time,
                    offset
                )
            } returns response
            //2-Call
            dataRepository = DataRepository(remoteData, localData)
            val actual: ApiResponse<ComicData> = dataRepository.getMarvelComics(
                key,
                hash,
                time,
                offset
            )
            //3-verify
            Assert.assertEquals(response.data.data, (actual as ApiResponse.Success).data)

        }
    }

    @Test
    fun `get comic remote with expire cache error`() {
        //1- Mock calls
        val key = ""
        val hash = ""
        val time = "Mon, 16 May 2022 17:00:01 GMT"
        val offset = 0
        runTest {
            val data = mockk<ApiResponse.Success<ComicResponse>> {
                every { data } returns mockk {
                    every { data } returns mockk(relaxed = true)
                }
            }
            val response = mockk<ApiResponse.Error>()
            val comicData = data.data.data ?: ComicData()
            comicData.timeStamp = "Sat, 14 May 2022 17:00:01 GMT"
            coEvery {
                localData.insertComic(comicData)
            } returns Unit

            coEvery {
                localData.getComic(offset)
            } returns null

            coEvery {
                remoteData.getComics(
                    key,
                    hash,
                    time,
                    offset
                )
            } returns response
            //2-Call
            dataRepository = DataRepository(remoteData, localData)
            val actual: ApiResponse<ComicData> = dataRepository.getMarvelComics(
                key,
                hash,
                time,
                offset
            )
            //3-verify
            Assert.assertEquals(100, (actual as ApiResponse.Error).error.statusCode)

        }
    }

    @Test
    fun `get character remote with expire cache error`() {
        //1- Mock calls
        val key = ""
        val hash = ""
        val time = "Mon, 16 May 2022 17:00:01 GMT"
        val offset = 0
        runTest {
            val data = mockk<ApiResponse.Success<CharactersResponse>> {
                every { data } returns mockk {
                    every { data } returns mockk(relaxed = true)
                }
            }
            val response = mockk<ApiResponse.Error>()
            val characterData = data.data.data ?: CharacterData()
            characterData.timeStamp = "Sat, 14 May 2022 17:00:01 GMT"
            coEvery {
                localData.insertCharacter(characterData)
            } returns Unit

            coEvery {
                localData.getCharacter(offset)
            } returns null

            coEvery {
                remoteData.getCharacters(
                    key,
                    hash,
                    time,
                    offset
                )
            } returns response
            //2-Call
            dataRepository = DataRepository(remoteData, localData)
            val actual: ApiResponse<CharacterData> = dataRepository.getMarvelCharacter(
                key,
                hash,
                time,
                offset
            )
            //3-verify
            Assert.assertEquals(100, (actual as ApiResponse.Error).error.statusCode)

        }
    }

    @After
    fun cleanUp() {
        clearAllMocks()
    }
}