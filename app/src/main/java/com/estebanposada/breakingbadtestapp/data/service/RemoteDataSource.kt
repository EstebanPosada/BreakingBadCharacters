package com.estebanposada.breakingbadtestapp.data.service

import com.estebanposada.breakingbadtestapp.data.service.model.Character

interface RemoteDataSource {
    suspend fun getCharacters():List<Character>
}