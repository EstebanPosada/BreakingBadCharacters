package com.estebanposada.breakingbadtestapp.data.source

import androidx.paging.DataSource
import com.estebanposada.breakingbadtestapp.data.database.Character

interface LocalDataSource {
    suspend fun isEmpty(): Boolean
    suspend fun saveCharacters(characters: List<Character>)
    suspend fun getCharacters(): List<Character>
    fun getFactoryCharacters(filter: String?): DataSource.Factory<Int, Character>
    suspend fun findById(id: Int): Character
    suspend fun update(character: Character)
}