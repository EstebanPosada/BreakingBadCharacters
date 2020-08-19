package com.estebanposada.breakingbadtestapp.data.source

import com.estebanposada.breakingbadtestapp.data.server.model.Character

interface RemoteDataSource {
    suspend fun getCharacters(limit: Int, offset: Int):List<Character>
}