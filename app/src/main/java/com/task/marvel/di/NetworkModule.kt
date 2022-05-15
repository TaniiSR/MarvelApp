package com.task.marvel.di

import com.task.marvel.data.remote.baseclient.RetroNetwork
import com.task.marvel.data.remote.microservices.marvelrepos.MarvelRepoRetroService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun providesGitRepoRetroService(): MarvelRepoRetroService =
        RetroNetwork().createService(MarvelRepoRetroService::class.java)
}