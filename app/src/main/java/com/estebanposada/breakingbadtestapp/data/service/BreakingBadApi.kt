package com.estebanposada.breakingbadtestapp.data.service

import com.estebanposada.breakingbadtestapp.data.service.model.Character
import retrofit2.http.GET

interface BreakingBadApi {
//    @GET("/api/characters?limit<LIMIT>&offset=<OFFSET>")
    @GET("/api/characters?limit=20")
    suspend fun getCharacters(): List<Character>
}