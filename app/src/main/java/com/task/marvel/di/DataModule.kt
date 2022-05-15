package com.task.marvel.di

import com.task.marvel.data.local.localservice.MarvelRepoDbService
import com.task.marvel.data.local.localservice.MarvelRepositoryLocal
import com.task.marvel.data.remote.microservices.marvelrepos.MarvelRepoApi
import com.task.marvel.data.remote.microservices.marvelrepos.MarvelRepositoryRemote
import com.task.marvel.domain.DataRepository
import com.task.marvel.domain.IDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun provideMarvelRemoteRepository(marvelRepositoryRemote: MarvelRepositoryRemote): MarvelRepoApi

    @Binds
    @Singleton
    abstract fun provideMarvelLocalRepository(marvelRepositoryLocal: MarvelRepositoryLocal): MarvelRepoDbService

    @Binds
    @Singleton
    abstract fun provideDataRepository(dataRepository: DataRepository): IDataRepository
}