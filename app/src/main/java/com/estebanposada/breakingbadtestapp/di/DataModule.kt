package com.estebanposada.breakingbadtestapp.di

import com.estebanposada.breakingbadtestapp.data.database.CharacterDao
import com.estebanposada.breakingbadtestapp.data.database.RoomDataSource
import com.estebanposada.breakingbadtestapp.data.repository.CharactersRepository
import com.estebanposada.breakingbadtestapp.data.server.BreakingBadApi
import com.estebanposada.breakingbadtestapp.data.server.RemoteDataSourceImpl
import com.estebanposada.breakingbadtestapp.data.source.LocalDataSource
import com.estebanposada.breakingbadtestapp.data.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@InstallIn(ApplicationComponent::class)
@Module
class DataModule {

    @Provides
    fun provideRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        dao: CharacterDao,
        scope: CoroutineScope,
        api: BreakingBadApi
    ) = CharactersRepository(localDataSource, remoteDataSource, dao, scope, api)

    @Provides
    fun provideLocalDataSource(dao: CharacterDao): LocalDataSource = RoomDataSource(dao)

    @Provides
    fun provideRemoteDataSource(service: BreakingBadApi): RemoteDataSource =
        RemoteDataSourceImpl(service)

    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)
}