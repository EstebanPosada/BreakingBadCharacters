package com.estebanposada.breakingbadtestapp.data.server

import com.estebanposada.breakingbadtestapp.data.server.model.Character
import com.estebanposada.breakingbadtestapp.data.source.RemoteDataSource


class RemoteDataSourceImpl(private val service: BreakingBadApi):
    RemoteDataSource {
    override suspend fun getCharacters(): List<Character> = service.getCharacters()
    override suspend fun getCharacters(limit: Int, offset: Int): List<Character> = service.getCharacters(limit, offset)
}