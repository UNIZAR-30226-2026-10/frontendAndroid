package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.LogrosRepository
import kotlinx.coroutines.flow.StateFlow

class ObtenerLogrosCase(
    private val email: StateFlow<String>,
    private val repository: LogrosRepository
) {
    suspend operator fun invoke(): List<LogroUsuario> {
        try {
            // 1. Obtenemos la lista global de logros desde el repositorio
            val globalAchievements = repository.getAllAchievements()

            // 2. Obtenemos las estadísticas del usuario para calcular el progreso
            val statsResponse = repository.getUserStats(email.value)

            // 3. Mapeamos los DTOs de la API a nuestro modelo de dominio LogroUsuario
            return globalAchievements.map { dto ->
                // Extraemos el valor de la estadística correspondiente al logro
                val progreso = statsResponse.stats[dto.claveMetrica] ?: 0

                LogroUsuario(
                    id = dto.id,
                    nombre = dto.nombre,
                    descripcion = dto.descripcion,
                    progresoActual = progreso,
                    progresoObjetivo = dto.objetivo,
                    tipoRecompensa = dto.tipoRecompensa,
                    valorRecompensa = dto.valorRecompensa,
                    esCompletado = progreso >= dto.objetivo,
                    recompensaReclamada = false // Esto se actualizará según la lógica de la API
                )
            }
        } catch (e: Exception) {
            // En caso de error de red, devolvemos una lista vacía para evitar el crash
            return emptyList()
        }
    }
}