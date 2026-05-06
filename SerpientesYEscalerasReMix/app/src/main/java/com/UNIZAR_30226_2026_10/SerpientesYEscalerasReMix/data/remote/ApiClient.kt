package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Body

interface ApiService {

    // FUNCIONES PARA PRUEBAS Y LOGGING EN MAIN_ACTIVITY
    @GET("achievements/ping")
    suspend fun pingAchievements(): Response<Unit>

    @GET("auth/ping")
    suspend fun pingAuth(): Response<Unit>

    @GET("cards/ping")
    suspend fun pingCards(): Response<Unit>

    @GET("lobbies/ping")
    suspend fun pingLobbies(): Response<Unit>

    @GET("matches/ping")
    suspend fun pingMatches(): Response<Unit>

    @GET("users/ping")
    suspend fun pingUsers(): Response<Unit>

    // FUNCIONES AUTH
    // ...
    @GET("achievements/")
    suspend fun getAllAchievements(): List<LogroDTO> // Obtener todos los logros

    @GET("users/{email}/stats")
    suspend fun getUserStats(@Path("email") email: String): StatsDTO // Obtener progreso

    @POST("users/{email}/achievements")
    suspend fun claimAchievement(
        @Path("email") email: String,
        @Body achievementId: Map<String, String>
    ): Response<Unit> // Reclamar logro
}

object ApiClient { // object = Singleton
        private val API_URL = "http://10.0.2.2:3000/api/"
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
