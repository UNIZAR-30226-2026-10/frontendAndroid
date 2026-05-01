package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.JugarCrearRepository
import kotlinx.coroutines.flow.StateFlow

class AbandonarExpulsarCase(
    private val repository: JugarCrearRepository,
    private val email: StateFlow<String>,
    private val username: StateFlow<String>,
    ) {
    suspend operator fun invoke(target: String) {
        if (target == email.value) { // Abandonar
            repository.abandonarExpulsar(email.value, email.value)
            repository.crearLobby(email.value, username.value)
        } else {
            repository.abandonarExpulsar(email.value, target)
        }
    }
}