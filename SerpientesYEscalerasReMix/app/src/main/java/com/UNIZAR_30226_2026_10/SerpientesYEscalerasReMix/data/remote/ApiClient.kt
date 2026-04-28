package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    // FUNCIONES PARA PRUEBAS Y LOGGING EN MAIN_ACTIVITY
    @GET("api/achievements/ping")
    suspend fun pingAchievements(): Response<Unit>

    @GET("api/auth/ping")
    suspend fun pingAuth(): Response<Unit>

    @GET("api/cards/ping")
    suspend fun pingCards(): Response<Unit>

    @GET("api/lobbies/ping")
    suspend fun pingLobbies(): Response<Unit>

    @GET("api/matches/ping")
    suspend fun pingMatches(): Response<Unit>

    @GET("api/users/ping")
    suspend fun pingUsers(): Response<Unit>

    // FUNCIONES AUTH
    // ...
}

object ApiClient { // object = Singleton
    private val API_URL = "http://syeremix.switzerlandnorth.cloudapp.azure.com/api/"
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
