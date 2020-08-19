package com.estebanposada.breakingbadtestapp.data.database

import androidx.paging.DataSource
import androidx.room.*

@Dao
interface CharacterDao {

    @Query("SELECT * FROM Character WHERE id = :id")
    fun findById(id: Int): Character

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCharacter(characters: List<Character>)

    @Update
    fun updateCharacter(character: Character)

    @Query("SELECT * FROM Character ORDER BY favorite DESC")
    fun getCharacters(): DataSource.Factory<Int, Character>

    @Query("SELECT * FROM Character WHERE name LIKE :filter ORDER BY favorite DESC")
    fun getCharactersFiltered(filter: String?): DataSource.Factory<Int, Character>
}