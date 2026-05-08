package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Lobby
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.JugarCrearRepository
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.PartidaRepository
import kotlinx.coroutines.flow.StateFlow

class SyncLobbyCase(
    private val repository: JugarCrearRepository,
    private val partidaRepository: PartidaRepository,
    private val username: StateFlow<String>,
    private val lobby: StateFlow<Lobby>
) {
    suspend operator fun invoke(onPartidaEmpezada: () -> Unit) {
        if (repository.lobbyId.value.isEmpty()) {
            repository.fetchLobbyByPlayer(username.value)
            // Si después de buscar por jugador sigue sin haber lobbyId, lo creamos
            if (repository.lobbyId.value.isEmpty()) {
                repository.crearLobby(username.value)
            }
        } else {
            repository.fetchLobby()
        }

        if (lobby.value.matchId != null) {
            partidaRepository.setMatchId(lobby.value.matchId!!)
            repository.clearMatchId()
            onPartidaEmpezada()
        }
    }
}
