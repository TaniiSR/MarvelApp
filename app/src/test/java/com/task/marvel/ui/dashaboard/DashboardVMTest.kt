package com.task.marvel.ui.dashaboard

import com.task.marvel.base.BaseTestCase
import com.task.marvel.base.getOrAwaitValue
import com.task.marvel.data.dtos.responsedtos.characters.CharacterData
import com.task.marvel.data.dtos.responsedtos.comics.ComicData
import com.task.marvel.data.remote.baseclient.ApiResponse
import com.task.marvel.domain.DataRepository
import com.task.marvel.domain.IDataRepository
import com.task.marvel.ui.dashboard.DashboardVM
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
class DashboardVMTest : BaseTestCase() {
    // Subject under test
    lateinit var viewModel: DashboardVM

    // Use a fake UseCase to be injected into the viewModel
    lateinit var dataRepo: IDataRepository

    @Before
    fun setUp() {
        dataRepo = mockk<DataRepository>()
    }

    @Test
    fun `get character repo list success`() {
        //1- Mock calls
        val key = ""
        val hash = ""
        val time = ""
        val offset = 0
        runTest {
            val response = mockk<ApiResponse.Success<CharacterData>> {
                every { data } returns mockk {
                    every { results } returns listOf()
                }
            }
            coEvery {
                dataRepo.getMarvelCharacter(
                    key,
                    hash,
                    time,
                    offset
                )
            } returns response
            //2-Call
            viewModel = DashboardVM(dataRepo)
            viewModel.getCharacters(apiKey = key, hash = hash, ts = time, offset)

            //3-verify
            Assert.assertEquals(true, viewModel.isCharactersSuccess.getOrAwaitValue())
        }
    }

    @Test
    fun `get comic data success`() {
        //1- Mock calls
        val key = ""
        val hash = ""
        val time = ""
        val offset = 0
        runTest {
            val response = mockk<ApiResponse.Success<ComicData>> {
                every { data } returns mockk {
                    every { results } returns listOf()
                }
            }
            coEvery {
                dataRepo.getMarvelComics(
                    key,
                    hash,
                    time,
                    offset
                )
            } returns response
            //2-Call
            viewModel = DashboardVM(dataRepo)
            viewModel.getComics(apiKey = key, hash = hash, ts = time, offset)

            //3-verify
            Assert.assertEquals(true, viewModel.isComicsSuccess.getOrAwaitValue())
        }
    }

    @Test
    fun `get character list error`() {
        //1- Mock calls
        val key = ""
        val hash = ""
        val time = ""
        val offset = 0
        runTest {
            val response = mockk<ApiResponse.Error>()
            coEvery {
                dataRepo.getMarvelCharacter(
                    key,
                    hash,
                    time,
                    offset
                )
            } returns response
            //2-Call
            viewModel = DashboardVM(dataRepo)
            viewModel.getCharacters(apiKey = key, hash = hash, ts = time, offset)

            //3-verify
            Assert.assertEquals(false, viewModel.isCharactersSuccess.getOrAwaitValue())
        }
    }

    @Test
    fun `get comic data error`() {
        //1- Mock calls
        val key = ""
        val hash = ""
        val time = ""
        val offset = 0
        runTest {
            val response = mockk<ApiResponse.Error>()
            coEvery {
                dataRepo.getMarvelComics(
                    key,
                    hash,
                    time,
                    offset
                )
            } returns response
            //2-Call
            viewModel = DashboardVM(dataRepo)
            viewModel.getComics(
                key,
                hash,
                time,
                offset
            )

            //3-verify
            Assert.assertEquals(false, viewModel.isComicsSuccess.getOrAwaitValue())

        }
    }


    @After
    fun cleanUp() {
        clearAllMocks()
    }
}