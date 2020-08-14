package com.estebanposada.breakingbadtestapp.ui

import androidx.lifecycle.ViewModel
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.estebanposada.breakingbadtestapp.data.repository.CharactersRepository
import com.estebanposada.breakingbadtestapp.data.database.Character
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainVIewModel @ViewModelInject constructor(private val repository: CharactersRepository) :
    ViewModel() {

    //    @Inject lateinit var repository
    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>>
        get() = _characters

    fun getCharacters() {
        viewModelScope.launch {
            val data = repository.getCharacters()
            _characters.value = data
        }
    }

    fun onCharacterClicked(id: Int){

    }
}