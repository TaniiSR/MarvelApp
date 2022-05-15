package com.task.marvel.ui.splash

import com.task.marvel.data.remote.baseclient.ApiResponse
import com.task.marvel.data.remote.microservices.marvelrepos.MarvelRepoApi
import com.task.marvel.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashVM @Inject constructor(
    private val marvelRepoApi: MarvelRepoApi
) : BaseViewModel(), ISplashVM {

    override fun getCharacter(
        apiKey: String,
        hashKey: String,
        time: String,
        offset: Int
    ) {
        launch {
            val response = marvelRepoApi.getCharacters(
                apiKey = apiKey,
                hash = hashKey,
                ts = time,
                offset = offset
            )
            withContext(Dispatchers.Main) {
                when (response) {
                    is ApiResponse.Success -> {

                    }
                    is ApiResponse.Error -> {

                    }
                }
            }
        }
    }
}