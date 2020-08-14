package com.estebanposada.breakingbadtestapp.di

import com.estebanposada.breakingbadtestapp.data.database.CharacterDao
import com.estebanposada.breakingbadtestapp.data.database.RoomDataSource
import com.estebanposada.breakingbadtestapp.data.repository.CharactersRepository
import com.estebanposada.breakingbadtestapp.data.service.BreakingBadApi
import com.estebanposada.breakingbadtestapp.data.service.LocalDataSource
import com.estebanposada.breakingbadtestapp.data.service.NetworkDataSource
import com.estebanposada.breakingbadtestapp.data.service.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
class DataModule {

    @Provides
    fun provideRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ) = CharactersRepository(localDataSource, remoteDataSource)

    @Provides
    fun provideLocalDataSource(dao: CharacterDao): LocalDataSource = RoomDataSource(dao)

    @Provides
    fun provideRemoteDataSource(service: BreakingBadApi): RemoteDataSource = NetworkDataSource(service)
}