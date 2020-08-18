package com.estebanposada.breakingbadtestapp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.estebanposada.breakingbadtestapp.data.database.Character
import com.estebanposada.breakingbadtestapp.data.source.LocalDataSource
import com.estebanposada.breakingbadtestapp.data.source.RemoteDataSource
import com.estebanposada.breakingbadtestapp.data.toDomain
import com.estebanposada.breakingbadtestapp.data.factory.CharacterDataFactory

class CharactersRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val dataFactory: CharacterDataFactory
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

        val localData = LivePagedListBuilder(
            localDataFactory,
            CharacterDataFactory.pagedListConfig()
        ).build()

        val remoteData = LivePagedListBuilder(
            dataFactory,
            CharacterDataFactory.pagedListConfig()
        ).build()
        return remoteData
    }

}