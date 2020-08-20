package com.estebanposada.breakingbadtestapp

import com.estebanposada.breakingbadtestapp.data.database.RoomDataSource
import com.estebanposada.breakingbadtestapp.data.repository.CharactersRepository
import com.estebanposada.breakingbadtestapp.ui.MainViewModel
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

    private lateinit var localDataSource: RoomDataSource

    private lateinit var vm: MainViewModel

    @Before
    fun setUp() {
        vm = MainViewModel(repository)
    }

    @Test
    fun getCharacter() {
        runBlocking {
            val character = mockedLocalCharacter.copy(char_id = 1)
            whenever(repository.getCharacterById(1)).thenReturn(character)

            val result = localDataSource.findById(1)

            assertEquals(character, result)
        }
    }

    @Test
    fun getCharacterById() {
        runBlocking {
            val character = mockedLocalCharacter.copy(char_id = 1)

            val result = repository.toggleFavoriteCharacter(character.char_id)

            verify(repository).toggleFavoriteCharacter(result.char_id)
        }
    }

    @Test
    fun checkFavorite() {
        runBlocking {
            val character = mockedLocalCharacter.copy(favorite = true)

            val result = repository.toggleFavoriteCharacter(character.char_id)

            assertFalse(result.favorite)
        }
    }

    @Test
    fun checkNoFavorite() {
        runBlocking {
            val character = mockedLocalCharacter.copy(favorite = false)

            val result = repository.toggleFavoriteCharacter(character.char_id)

            assertTrue(result.favorite)
        }
    }

    @Test
    fun testPaging() {
        runBlocking {
            val data = vm.detail.getValueForTest()!!
            val test = vm.detail.getValueForTest()
            assertEquals(data, test)
        }
    }
}