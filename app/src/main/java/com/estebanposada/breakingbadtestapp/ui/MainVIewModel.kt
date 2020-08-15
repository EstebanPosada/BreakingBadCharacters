package com.estebanposada.breakingbadtestapp.ui

import androidx.lifecycle.ViewModel
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.estebanposada.breakingbadtestapp.data.repository.CharactersRepository
import com.estebanposada.breakingbadtestapp.data.database.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainVIewModel @ViewModelInject constructor(private val repository: CharactersRepository) :
    ViewModel() {

    //    @Inject lateinit var repository
    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>>
        get() = _characters

    private val _detail = MutableLiveData<Character>()
    val detail: LiveData<Character>
        get() = _detail

    fun getCharacters() {
        viewModelScope.launch {
            val data = withContext(Dispatchers.IO) { repository.getCharacters() }
            _characters.value = data
        }
    }

    fun onFavoriteClicked(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val data = repository.toggleFavoriteCharacter(id)
                _detail.postValue(data)
            }
        }
    }

    fun getCharacterInfo(id: Int) {
        viewModelScope.launch {
            val data = withContext(Dispatchers.IO) { repository.getCharacterById(id) }
            _detail.value = data
        }
    }
}