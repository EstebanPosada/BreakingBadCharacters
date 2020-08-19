package com.estebanposada.breakingbadtestapp.data.database

import androidx.paging.DataSource
import com.estebanposada.breakingbadtestapp.data.source.LocalDataSource

class RoomDataSource(private val dao: CharacterDao) :
    LocalDataSource {

    override suspend fun findById(id: Int): Character = dao.findById(id)

    override suspend fun update(character: Character) {
        dao.updateCharacter(character)
    }

    override fun getFactoryCharacters(): DataSource.Factory<Int, Character> = dao.getCharacters()

    override fun getFactoryCharactersFiltered(filter: String): DataSource.Factory<Int, Character> =
        dao.getCharactersFiltered(filter)
}