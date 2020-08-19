package com.estebanposada.breakingbadtestapp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.estebanposada.breakingbadtestapp.data.database.Character
import com.estebanposada.breakingbadtestapp.data.database.CharacterDao
import com.estebanposada.breakingbadtestapp.data.factory.CharacterBoundaryCallback
import com.estebanposada.breakingbadtestapp.data.source.LocalDataSource
import com.estebanposada.breakingbadtestapp.data.source.RemoteDataSource
import com.estebanposada.breakingbadtestapp.data.toDomain
import com.estebanposada.breakingbadtestapp.data.factory.CharacterDataFactory
import com.estebanposada.breakingbadtestapp.data.server.BreakingBadApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CharactersRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val dataFactory: CharacterDataFactory,
    private val dao: CharacterDao,
    private val scope: CoroutineScope,
    private val api: BreakingBadApi
) {

    suspend fun getCharacters(): List<Character> {
        if (localDataSource.isEmpty()) {
            val data = remoteDataSource.getCharacters().map { it.toDomain() }
            localDataSource.saveCharacters(data)
        }
        return localDataSource.getCharacters()
    }

    suspend fun getCharacters(limit: Int, offset: Int): List<Character> =
        remoteDataSource.getCharacters(limit, offset).map { it.toDomain() }

    suspend fun getCharacterById(id: Int): Character = localDataSource.findById(id)

    suspend fun toggleFavoriteCharacter(id: Int): Character {
        val character = getCharacterById(id)

        return with(character) {
            copy(favorite = !favorite).also { localDataSource.update(it) }
        }
    }

    fun getData(filter: String?): LiveData<PagedList<Character>> {
        val query = "%${filter?.replace(' ', '%')}%"
        val localDataFactory = localDataSource.getFactoryCharacters(query)

        scope.launch {
            val data = localDataSource.getCharacters()
        }

        val localData = LivePagedListBuilder(
            localDataFactory,
            CharacterDataFactory.pagedListConfig()
        )
//            .setBoundaryCallback(CharacterBoundaryCallback(dao, scope, api))
            .build()

//        val remoteData = LivePagedListBuilder(
//            dataFactory,
//            CharacterDataFactory.pagedListConfig()
//        ).build()
        return localData
    }

}