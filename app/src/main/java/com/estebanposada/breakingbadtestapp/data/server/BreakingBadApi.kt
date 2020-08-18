package com.estebanposada.breakingbadtestapp.data.server

import com.estebanposada.breakingbadtestapp.data.server.model.Character
import retrofit2.http.GET
import retrofit2.http.Query

interface BreakingBadApi {
    //    @GET("/api/characters?limit<LIMIT>&offset=<OFFSET>")
    @GET("/api/characters?limit=20")
    suspend fun getCharacters(): List<Character>

    @GET("/api/characters")
    suspend fun getCharacters(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): List<Character>
}