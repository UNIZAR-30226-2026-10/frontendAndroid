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

            // Recorre los resultados y verifica que cada uno sea exitoso, imprimiendo un log en caso de acierto o fallo
            results.forEachIndexed { index, response ->
                val endpointName = when (index) {
                    0 -> "Achievements"
                    1 -> "Auth"
                    2 -> "Cards"
                    3 -> "Lobbies"
                    4 -> "Matches"
                    5 -> "Users"
                    else -> "Unknown"
                }
                if (response.isSuccessful) {
                    Log.d("RETROFIT_TEST", "✅ Ping a $endpointName exitoso")
                } else {
                    Log.e("RETROFIT_TEST", "❌ Ping a $endpointName fallido con código ${response.code()}")
                }
            }


            // Verifica que TODAS las respuestas sean exitosas (código 2xx)
            results.all { it.isSuccessful }
        } catch (e: Exception) {
            Log.e("RETROFIT_ERROR", "Fallo en ping: ${e.message}")
            false
        }
    }
}