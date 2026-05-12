package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.repository

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.ApiService
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.LogroDTO
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.StatsDTO
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
        if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            throw Exception("Error al obtener logros: ${response.code()} ${response.message()}")
        }
    }

    override suspend fun getUserStats(email: String): StatsDTO = withContext(Dispatchers.IO) {
        val response = apiService.getUserStats(email)
        if (response.isSuccessful) {
            response.body() ?: throw Exception("Respuesta vacía al obtener stats")
        } else {
            throw Exception("Error al obtener stats: ${response.code()} ${response.message()}")
        }
    }

    override suspend fun getClaimedAchievements(email: String): List<String> = withContext(Dispatchers.IO) {
        val response = apiService.getClaimedAchievements(email)
        if (response.isSuccessful) {
            response.body()?.ids ?: emptyList()
        } else {
            throw Exception("Error al obtener logros reclamados: ${response.code()} ${response.message()}")
        }
    }

    override suspend fun postClaimAchievement(email: String, achievementId: String) = withContext(Dispatchers.IO) {
        val response = apiService.claimAchievement(email, mapOf("achievement_id" to achievementId))
        if (!response.isSuccessful) {
            throw Exception("Error al reclamar logro: ${response.code()} ${response.message()}")
        }
    }
}