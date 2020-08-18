package com.estebanposada.breakingbadtestapp.data.database

import androidx.paging.DataSource
import androidx.room.*

@Dao
interface CharacterDao {

    @Query("SELECT * FROM Character ORDER BY favorite DESC")
    fun getAll(): List<Character>

    @Query("SELECT * FROM Character WHERE id = :id")
    fun findById(id: Int): Character

    @Query("SELECT COUNT(id) FROM Character")
    fun characterCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCharacter(characters: List<Character>)

    @Update
    fun updateCharacter(character: Character)

    @Query("SELECT * FROM Character WHERE name = :filter " +
            "OR nickname = :filter ORDER BY favorite DESC")
    fun getCharacters(filter: String?): DataSource.Factory<Int, Character>
}