package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.LogroDTO
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.StatsDTO

interface LogrosRepository {
    // Obtiene la definición global de todos los logros
    suspend fun getAllAchievements(): List<LogroDTO>

    // Obtiene las estadísticas del usuario para calcular el progreso de cada logro
    suspend fun getUserStats(email: String): StatsDTO

    // Obtiene los IDs de los logros que el usuario ya ha reclamado
    suspend fun getClaimedAchievements(email: String): List<String>

    // Envía la petición para marcar un logro como reclamado
    suspend fun postClaimAchievement(email: String, achievementId: String)
}