package com.estebanposada.breakingbadtestapp

import com.estebanposada.breakingbadtestapp.data.database.RoomDataSource
import com.estebanposada.breakingbadtestapp.data.repository.CharactersRepository
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {

    @Mock
    lateinit var repository: CharactersRepository

    lateinit var localDataSource: RoomDataSource

    @Before
    fun setUp() {

    }

    @Test
    fun getCharacter() {
        runBlocking {
            val character = mockedLocalCharacter.copy(id = 1)
            whenever(repository.getCharacterById(1)).thenReturn(character)

            val result = localDataSource.findById(1)

            assertEquals(character, result)
        }
    }

    @Test
    fun getCharacters() {
        runBlocking {
            val characters = listOf(mockedLocalCharacter.copy(id = 1))
            whenever(repository.getCharacters(1, 0)).thenReturn(characters)

            val result = localDataSource.getCharacters()

            assertEquals(characters, result)
        }
    }

    @Test
    fun getCh() {
        runBlocking {
            val character = mockedLocalCharacter.copy(id = 1)

            val result = repository.toggleFavoriteCharacter(character.id)

            verify(repository).toggleFavoriteCharacter(result.id)

        }
    }

    @Test
    fun checkFavorite() {
        runBlocking {
            val character = mockedLocalCharacter.copy(favorite = true)

            val result = repository.toggleFavoriteCharacter(character.id)

            assertFalse(result.favorite)
        }
    }

    @Test
    fun checkNoFavorite() {
        runBlocking {
            val character = mockedLocalCharacter.copy(favorite = false)

            val result = repository.toggleFavoriteCharacter(character.id)

            assertTrue(result.favorite)
        }
    }


    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}