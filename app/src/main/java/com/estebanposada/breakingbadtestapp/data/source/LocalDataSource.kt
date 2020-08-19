package com.estebanposada.breakingbadtestapp.data.source

import androidx.paging.DataSource
import com.estebanposada.breakingbadtestapp.data.database.Character

interface LocalDataSource {
    fun getFactoryCharacters(): DataSource.Factory<Int, Character>
    fun getFactoryCharactersFiltered(filter: String): DataSource.Factory<Int, Character>
    suspend fun findById(id: Int): Character
    suspend fun update(character: Character)
}