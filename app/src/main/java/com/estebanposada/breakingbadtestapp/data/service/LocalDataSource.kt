package com.estebanposada.breakingbadtestapp.data.service

import com.estebanposada.breakingbadtestapp.data.database.Character

interface LocalDataSource {
    suspend fun isEmpty(): Boolean
    suspend fun saveMovies(characters: List<Character>)
    suspend fun getPopularMovies(): List<Character>
    suspend fun findById(id: Int): Character
    suspend fun update(character: Character)
}