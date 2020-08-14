package com.estebanposada.breakingbadtestapp.data.database

import com.estebanposada.breakingbadtestapp.data.service.LocalDataSource
import javax.inject.Inject

class RoomDataSource @Inject constructor(private val dao: CharacterDao): LocalDataSource {

    override suspend fun isEmpty(): Boolean = dao.characterCount() <= 0

    override suspend fun saveMovies(characters: List<Character>) {
        dao.insertCharacter(characters)
    }

    override suspend fun getPopularMovies(): List<Character> = dao.getAll()

    override suspend fun findById(id: Int): Character = dao.findById(id)

    override suspend fun update(character: Character) {
        dao.updateCharacter(character)
    }
}