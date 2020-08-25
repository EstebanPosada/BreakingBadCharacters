package com.estebanposada.breakingbadtestapp.di

import com.estebanposada.breakingbadtestapp.data.database.CharacterDao
import com.estebanposada.breakingbadtestapp.data.database.RoomDataSource
import com.estebanposada.breakingbadtestapp.data.factory.CharacterBoundaryCallback
import com.estebanposada.breakingbadtestapp.data.repository.CharactersRepository
import com.estebanposada.breakingbadtestapp.data.server.BreakingBadApi
import com.estebanposada.breakingbadtestapp.data.source.LocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DataModule {

    @Provides
    fun provideRepository(
        localDataSource: LocalDataSource,
        boundaryCallback: CharacterBoundaryCallback
    ) = CharactersRepository(localDataSource, boundaryCallback)

    @Provides
    fun provideLocalDataSource(dao: CharacterDao): LocalDataSource = RoomDataSource(dao)

    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)

    @Provides
    @Singleton
    fun provideBoundaryCallback(
        dao: CharacterDao,
        scope: CoroutineScope,
        api: BreakingBadApi
    ) = CharacterBoundaryCallback(dao, scope, api)
}