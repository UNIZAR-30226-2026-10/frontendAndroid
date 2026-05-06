package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.repository

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.ApiService
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.LogroDTO
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.StatsDTO
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.LogrosRepository

class LogrosRepositoryImpl(
    private val apiService: ApiService
) : LogrosRepository {

    override suspend fun getAllAchievements(): List<LogroDTO> {
        // Llamada directa a Retrofit
        return apiService.getAllAchievements()
    }

    override suspend fun getUserStats(email: String): StatsDTO {
        return apiService.getUserStats(email)
    }

    override suspend fun postClaimAchievement(email: String, achievementId: String) {
        val body = mapOf("achievement_id" to achievementId)
        val response = apiService.claimAchievement(email, body)

        if (!response.isSuccessful) {
            throw Exception("Error al reclamar logro: ${response.code()}")
        }
    }
}