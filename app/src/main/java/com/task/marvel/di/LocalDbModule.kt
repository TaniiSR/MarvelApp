package com.task.marvel.di

import android.content.Context
import androidx.room.Room
import com.task.marvel.data.local.db.MarvelAppDB
import com.task.marvel.data.local.localservice.MarvelLocalDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDbModule {
    @Provides
    fun provideMarvelDao(appDatabase: MarvelAppDB): MarvelLocalDao {
        return appDatabase.MarvelLocalDao()
    }

    @Provides
    @Singleton
    fun provideAppDB(@ApplicationContext appContext: Context): MarvelAppDB {
        return Room.databaseBuilder(
            appContext,
            MarvelAppDB::class.java,
            "MarvelAppDB"
        ).build()
    }

}