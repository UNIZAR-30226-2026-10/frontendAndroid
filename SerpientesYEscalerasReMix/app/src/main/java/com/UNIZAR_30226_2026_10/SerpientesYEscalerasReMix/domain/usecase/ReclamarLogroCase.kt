package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.LogrosRepository
import kotlinx.coroutines.flow.StateFlow

class ReclamarLogroCase(
    private val email: StateFlow<String>,
    private val repository: LogrosRepository
) {
    /**
     * Ejecuta la acción de reclamar un logro enviando el ID al servidor.
     * @param achievementId Identificador único del logro que el usuario desea reclamar.
     */
    suspend operator fun invoke(achievementId: String) {
        try {
            // Realizamos la llamada al repositorio pasando el email actual y el ID del logro
            repository.postClaimAchievement(
                email = email.value,
                achievementId = achievementId
            )
        } catch (e: Exception) {
            // Aquí podrías lanzar una excepción personalizada o loguear el error
            throw e
        }
    }
}