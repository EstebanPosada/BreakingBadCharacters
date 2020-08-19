package com.estebanposada.breakingbadtestapp.data.server.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.estebanposada.breakingbadtestapp.data.database.Character

data class CharacterResult(
    val data: LiveData<PagedList<Character>>
)