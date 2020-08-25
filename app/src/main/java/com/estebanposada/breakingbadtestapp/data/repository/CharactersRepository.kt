package com.estebanposada.breakingbadtestapp.data.repository

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.estebanposada.breakingbadtestapp.data.database.Character
import com.estebanposada.breakingbadtestapp.data.factory.CharacterBoundaryCallback
import com.estebanposada.breakingbadtestapp.data.server.model.CharacterResult
import com.estebanposada.breakingbadtestapp.data.source.LocalDataSource

class CharactersRepository(
    private val localDataSource: LocalDataSource,
    private val boundaryCallback: CharacterBoundaryCallback
) {

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
            pagedListConfig()
        ).setBoundaryCallback(boundaryCallback)
            .build()
        return CharacterResult(data)
    }

    fun getData(filter: String): CharacterResult {
        val query = "%${filter?.replace(' ', '%')}%"
        val localDataFactory = localDataSource.getFactoryCharactersFiltered(query)

        val data = LivePagedListBuilder(
            localDataFactory,
            pagedListConfig()
        ).setBoundaryCallback(boundaryCallback)
            .build()
        return CharacterResult(data)
    }

    companion object {
        const val PAGE_SIZE = 15

        fun pagedListConfig() = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()
    }

}