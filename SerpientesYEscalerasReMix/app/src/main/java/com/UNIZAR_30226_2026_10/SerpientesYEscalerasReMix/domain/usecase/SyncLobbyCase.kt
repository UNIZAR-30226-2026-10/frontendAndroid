package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.JugarCrearRepository
import kotlinx.coroutines.flow.StateFlow

class SyncLobbyCase(
    private val repository: JugarCrearRepository,
    private val username: StateFlow<String>
) {
    suspend operator fun invoke() {
        if (repository.lobbyId.value.isEmpty()) {
            repository.fetchLobbyByPlayer(username.value)
            // Si después de buscar por jugador sigue sin haber lobbyId, lo creamos
            if (repository.lobbyId.value.isEmpty()) {
                repository.crearLobby(username.value)
            }
        } else {
            repository.fetchLobby()
        }
    }
}
