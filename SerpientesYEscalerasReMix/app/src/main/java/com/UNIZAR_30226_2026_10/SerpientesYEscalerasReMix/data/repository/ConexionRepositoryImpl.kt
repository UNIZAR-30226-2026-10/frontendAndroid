package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.repository

import android.util.Log
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.ApiService
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.ConexionRepository

class ConexionRepositoryImpl(private val apiService: ApiService) : ConexionRepository {
    override suspend fun pruebaConexionAPI(): Boolean {
        return try {
            val results = listOf(
                apiService.pingAchievements(),
                apiService.pingAuth(),
                apiService.pingCards(),
                apiService.pingLobbies(),
                apiService.pingMatches(),
                apiService.pingUsers()
            )
            // Verifica que TODAS las respuestas sean exitosas (código 2xx)
            results.all { it.isSuccessful }
        } catch (e: Exception) {
            Log.e("RETROFIT_ERROR", "Fallo en ping: ${e.message}")
            false
        }
    }
}