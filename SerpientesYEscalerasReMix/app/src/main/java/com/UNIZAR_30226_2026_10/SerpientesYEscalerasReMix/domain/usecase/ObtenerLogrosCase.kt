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
        val reclamados = statsResponse.logrosCompletados.toSet()
        // 2. Mapeamos los DTOs al modelo de dominio LogroUsuario
        return globalAchievements.map { dto ->
            val progreso = when (dto.tipoRecompensa) {
                "Victorias"            -> statsResponse.victorias
                "Partidas"             -> statsResponse.partidasJugadas
                "Derrotas"             -> statsResponse.derrotas
                "SEP"                  -> statsResponse.sep
                "CartasJugadas"        -> statsResponse.cartasJugadas
                "CartasLegendarias"    -> statsResponse.cartasLegendarias
                "NumeroAmigos"         -> statsResponse.numeroAmigos
                "LogrosDesbloqueados"  -> statsResponse.logrosCompletados.size
                else                   -> 0
            }
            LogroUsuario(                              // ← esto faltaba
                id                  = dto.id,
                nombre              = dto.nombre,
                descripcion         = dto.descripcion,
                progresoActual      = progreso,
                progresoObjetivo    = dto.objetivo,
                tipoRecompensa      = dto.tipoRecompensa,
                valorRecompensa     = dto.valorRecompensa?.toString() ?: "",
                imagen              = dto.imagen,
                esCompletado        = progreso >= dto.objetivo,
                recompensaReclamada = dto.id in reclamados
            )
        }
    }
}