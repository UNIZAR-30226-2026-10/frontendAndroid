package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.repository

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.ApiService
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.LogroDTO
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.StatsDTO
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.ApiClient
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.LogrosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LogrosRepositoryImpl(
    // FIX: recibe ApiService por constructor en lugar de importar ApiClient.apiService
    // directamente, lo que rompía la inyección de dependencias y el ApiClient con cookies
    private val apiService: ApiService
) : LogrosRepository {

    override suspend fun getAllAchievements(): List<LogroDTO> = withContext(Dispatchers.IO) {
        val response = apiService.getAllAchievements()
        when {
            response.isSuccessful -> response.body()?.achievements ?: emptyList()
            response.code() == 404 -> emptyList()
            else -> throw Exception("Error al obtener logros: ${response.code()} ${response.message()}")
        }
    }

    override suspend fun getUserStats(email: String): StatsDTO = withContext(Dispatchers.IO) {
        val response = apiService.getUserStats(email)
        when {
            response.isSuccessful -> response.body() ?: StatsDTO()
            response.code() == 404 -> StatsDTO()
            else -> throw Exception("Error al obtener stats: ${response.code()} ${response.message()}")
        }
    }

    override suspend fun getClaimedAchievements(email: String): List<String> = withContext(Dispatchers.IO) {
        val response = apiService.getClaimedAchievements(email)
        when {
            response.isSuccessful -> response.body()?.ids ?: emptyList()
            response.code() == 404 -> emptyList()   // ← sin logros reclamados aún
            else -> throw Exception("Error al obtener logros reclamados: ${response.code()} ${response.message()}")
        }
    }

    override suspend fun postClaimAchievement(email: String, achievementId: String) = withContext(Dispatchers.IO) {
        // Usar el apiService inyectado, igual que el resto de métodos
        val body = mapOf("achievement_id" to achievementId)

        android.util.Log.d("RECLAMAR", "POST users/$email/achievements body=$body")

        val response = apiService.claimAchievement(email, body)

        if (!response.isSuccessful) {
            throw Exception("Error ${response.code()}: ${response.message()}")
        }
    }
}