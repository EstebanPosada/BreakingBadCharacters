package com.estebanposada.breakingbadtestapp.data.database

import androidx.paging.DataSource
import com.estebanposada.breakingbadtestapp.data.source.LocalDataSource
import javax.inject.Inject

class RoomDataSource @Inject constructor(private val dao: CharacterDao) :
    LocalDataSource {

    override suspend fun isEmpty(): Boolean = dao.characterCount() <= 0

    override suspend fun saveCharacters(characters: List<Character>) {
        dao.insertCharacter(characters)
    }

    override suspend fun getCharacters(): List<Character> = dao.getAll()

    override suspend fun findById(id: Int): Character = dao.findById(id)

    override suspend fun update(character: Character) {
        dao.updateCharacter(character)
    }

    fun getData(fil: String) = dao.getCharacterss(fil)

    fun getAll() = dao.getAll()

     override fun getFactoryCharacters(filter: String?): DataSource.Factory<Int, Character> = dao.getCharacters(filter)
}