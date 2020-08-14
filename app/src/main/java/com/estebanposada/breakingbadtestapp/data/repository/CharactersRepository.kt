package com.estebanposada.breakingbadtestapp.data.repository

import com.estebanposada.breakingbadtestapp.data.database.Character
import com.estebanposada.breakingbadtestapp.data.service.LocalDataSource
import com.estebanposada.breakingbadtestapp.data.service.RemoteDataSource
import com.estebanposada.breakingbadtestapp.data.toDomain

class CharactersRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getCharacters(): List<Character> {

        val data = remoteDataSource.getCharacters().map { it.toDomain() }
        return data
    }
}