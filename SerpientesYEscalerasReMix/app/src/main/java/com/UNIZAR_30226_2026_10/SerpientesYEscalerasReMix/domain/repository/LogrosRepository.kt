package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.LogroDTO
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.StatsDTO

interface LogrosRepository {
    // Obtiene la definición de todos los logros del servidor[cite: 1, 8]
    suspend fun getAllAchievements(): List<LogroDTO>

    // Obtiene las estadísticas de un usuario específico para calcular progreso[cite: 1, 8]
    suspend fun getUserStats(email: String): StatsDTO

    // Envía la petición para marcar un logro como reclamado[cite: 1, 9]
    suspend fun postClaimAchievement(email: String, achievementId: String)
}