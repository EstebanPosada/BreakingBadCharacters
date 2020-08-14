package com.estebanposada.breakingbadtestapp.data.service

import com.estebanposada.breakingbadtestapp.data.service.model.Character


class NetworkDataSource(private val service: BreakingBadApi): RemoteDataSource {
    override suspend fun getCharacters(): List<Character> = service.getCharacters()
}