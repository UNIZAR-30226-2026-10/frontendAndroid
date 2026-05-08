package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.JugarCrearRepository
import kotlinx.coroutines.flow.StateFlow

class AbandonarExpulsarCase(
    private val repository: JugarCrearRepository,
    private val username: StateFlow<String>,
    ) {
    suspend operator fun invoke(targetUsername: String) {
        repository.abandonarExpulsar(username.value, targetUsername)
        if (targetUsername == username.value) { // Abandonar
            repository.crearLobby(username.value)
        }
    }
}
