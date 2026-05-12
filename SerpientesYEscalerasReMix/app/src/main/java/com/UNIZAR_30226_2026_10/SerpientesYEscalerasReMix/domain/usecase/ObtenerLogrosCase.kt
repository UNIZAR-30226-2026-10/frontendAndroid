package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.LogrosRepository
import kotlinx.coroutines.flow.StateFlow

class ObtenerLogrosCase(
    private val email: StateFlow<String>,
    private val repository: LogrosRepository
) {
    suspend operator fun invoke(): List<LogroUsuario> {

        // 1. Obtenemos la lista global de logros y los ya reclamados en paralelo
        val globalAchievements  = repository.getAllAchievements()
        val statsResponse       = repository.getUserStats(email.value)
        // FIX: obtenemos los IDs de logros ya reclamados para marcar recompensaReclamada
        val reclamados          = repository.getClaimedAchievements(email.value).toSet()

        // 2. Mapeamos los DTOs al modelo de dominio LogroUsuario
        return globalAchievements.map { dto ->
            val progreso = statsResponse.stats[dto.claveMetrica] ?: 0
            LogroUsuario(
                id                 = dto.id,
                nombre             = dto.nombre,
                descripcion        = dto.descripcion,
                progresoActual     = progreso,
                progresoObjetivo   = dto.objetivo,
                tipoRecompensa     = dto.tipoRecompensa,
                valorRecompensa    = dto.valorRecompensa,
                imagen             = dto.imagen,
                esCompletado       = progreso >= dto.objetivo,
                // FIX: antes siempre era false; ahora refleja el estado real del servidor
                recompensaReclamada = dto.id in reclamados
            )
        }
    }
}