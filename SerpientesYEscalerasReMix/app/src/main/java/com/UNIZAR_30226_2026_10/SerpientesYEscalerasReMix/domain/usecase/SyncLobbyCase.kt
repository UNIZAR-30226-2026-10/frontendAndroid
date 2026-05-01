package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.JugarCrearRepository
import kotlinx.coroutines.flow.StateFlow

class SyncLobbyCase(
    private val repository: JugarCrearRepository,
    private val email: StateFlow<String>,
    private val username: StateFlow<String>
) {
    suspend operator fun invoke() {
        if (repository.lobbyId.equals("")) {
            repository.crearLobby(email.value, username.value)
        } else {
            repository.fetchLobby()
        }
    }
}