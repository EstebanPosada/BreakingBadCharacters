package com.estebanposada.breakingbadtestapp.data.repository

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.estebanposada.breakingbadtestapp.data.database.Character
import com.estebanposada.breakingbadtestapp.data.database.CharacterDao
import com.estebanposada.breakingbadtestapp.data.factory.CharacterBoundaryCallback
import com.estebanposada.breakingbadtestapp.data.server.BreakingBadApi
import com.estebanposada.breakingbadtestapp.data.server.model.CharacterResult
import com.estebanposada.breakingbadtestapp.data.source.LocalDataSource
import kotlinx.coroutines.CoroutineScope

class CharactersRepository(
    private val localDataSource: LocalDataSource,
    private val dao: CharacterDao,
    private val scope: CoroutineScope,
    private val api: BreakingBadApi
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
        ).setBoundaryCallback(CharacterBoundaryCallback(dao, scope, api))
            .build()
        return CharacterResult(data)
    }

    fun getData(filter: String): CharacterResult {
        val query = "%${filter?.replace(' ', '%')}%"
        val localDataFactory = localDataSource.getFactoryCharactersFiltered(query)

        val data = LivePagedListBuilder(
            localDataFactory,
            pagedListConfig()
        ).setBoundaryCallback(CharacterBoundaryCallback(dao, scope, api))
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