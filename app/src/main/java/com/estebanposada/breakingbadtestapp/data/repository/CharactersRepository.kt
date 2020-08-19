package com.estebanposada.breakingbadtestapp.data.repository

import androidx.paging.LivePagedListBuilder
import com.estebanposada.breakingbadtestapp.data.database.Character
import com.estebanposada.breakingbadtestapp.data.database.CharacterDao
import com.estebanposada.breakingbadtestapp.data.factory.CharacterBoundaryCallback
import com.estebanposada.breakingbadtestapp.data.factory.CharacterDataFactory
import com.estebanposada.breakingbadtestapp.data.server.BreakingBadApi
import com.estebanposada.breakingbadtestapp.data.server.model.CharacterResult
import com.estebanposada.breakingbadtestapp.data.source.LocalDataSource
import com.estebanposada.breakingbadtestapp.data.source.RemoteDataSource
import com.estebanposada.breakingbadtestapp.data.toDomain
import kotlinx.coroutines.CoroutineScope

class CharactersRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val dataFactory: CharacterDataFactory,
    private val dao: CharacterDao,
    private val scope: CoroutineScope,
    private val api: BreakingBadApi
) {

    suspend fun getCharacters(limit: Int, offset: Int): List<Character> =
        remoteDataSource.getCharacters(limit, offset).map { it.toDomain() }

    suspend fun getCharacterById(id: Int): Character = localDataSource.findById(id)

    suspend fun toggleFavoriteCharacter(id: Int): Character {
        val character = getCharacterById(id)

        return with(character) {
            copy(favorite = !favorite).also { localDataSource.update(it) }
        }
    }

    fun getCharacters(): CharacterResult {
        val localDataFactory = localDataSource.getFactoryCharacters()

        val data = LivePagedListBuilder(
            localDataFactory,
            CharacterDataFactory.pagedListConfig()
        ).setBoundaryCallback(CharacterBoundaryCallback(dao, scope, api))
            .build()
        return CharacterResult(data)
    }

    fun getData(filter: String): CharacterResult {
        val query = "%${filter?.replace(' ', '%')}%"
        val localDataFactory = localDataSource.getFactoryCharactersFiltered(query)

        val data = LivePagedListBuilder(
            localDataFactory,
            CharacterDataFactory.pagedListConfig()
        ).setBoundaryCallback(CharacterBoundaryCallback(dao, scope, api))
            .build()
        return CharacterResult(data)
    }

}