package com.juandelarosa.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FixerService {
    @GET("symbols")
    suspend fun getCurrencies(
        @Query("access_key") apiKey : String = Config.apikey,
    ): Response<String>

    @GET("latest")
    suspend fun getLatest(
        @Query("access_key") apiKey : String = Config.apikey,
        @Query("base") base : String
    ): Response<String>
}
