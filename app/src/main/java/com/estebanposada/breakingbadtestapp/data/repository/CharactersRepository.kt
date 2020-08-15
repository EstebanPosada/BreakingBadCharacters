package com.estebanposada.breakingbadtestapp.data.repository

import com.estebanposada.breakingbadtestapp.data.database.Character
import com.estebanposada.breakingbadtestapp.data.service.LocalDataSource
import com.estebanposada.breakingbadtestapp.data.service.RemoteDataSource
import com.estebanposada.breakingbadtestapp.data.toDomain

class CharactersRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getCharacters(): List<Character> {
        if (localDataSource.isEmpty()) {
            val data = remoteDataSource.getCharacters().map { it.toDomain() }
            localDataSource.saveCharacters(data)
        }
        return localDataSource.getCharacters()
    }

    suspend fun getCharacterById(id: Int): Character = localDataSource.findById(id)

    suspend fun toggleFavoriteCharacter(id: Int): Character {
        val character = getCharacterById(id)
        return with(character) {
            copy(favorite = !favorite).also { localDataSource.update(it) }
        }
    }
}