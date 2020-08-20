package com.estebanposada.breakingbadtestapp.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.estebanposada.breakingbadtestapp.data.database.Character
import com.estebanposada.breakingbadtestapp.data.repository.CharactersRepository
import com.estebanposada.breakingbadtestapp.data.server.model.CharacterResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel @ViewModelInject constructor(private val repository: CharactersRepository) :
    ViewModel() {

    private val queryLiveData = MutableLiveData<String>()
    private val result: LiveData<CharacterResult> = Transformations.map(queryLiveData) {
        if (it.isEmpty()) repository.getCharacters() else repository.getData(it)
    }

    val characters: LiveData<PagedList<Character>> =
        Transformations.switchMap(result) { it -> it.data }

    private val _detail = MutableLiveData<Character>()
    val detail: LiveData<Character>
        get() = _detail

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

    fun search(filter: String) {
        queryLiveData.postValue(filter)
    }
}