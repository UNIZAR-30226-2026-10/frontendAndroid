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
        val currentLobbyId = repository.lobbyId.value

        if (currentLobbyId.isEmpty()) {
            // Intentamos recuperar si el jugador ya pertenece a algún lobby en el servidor
            val exito = repository.fetchLobbyByPlayer(username.value)

            // En caso negativo se crea el lobby
            if (!exito) {
                repository.crearLobby(username.value)
            }
        } else {
            // Si ya tenemos un ID local, simplemente actualizamos sus datos
            repository.fetchLobby()
        }

        val lobbyActual = lobby.value
        if (!lobbyActual.matchId.isNullOrBlank()) {
            if (partidaRepository.matchId.value != lobbyActual.matchId) {
                partidaRepository.setMatchId(lobbyActual.matchId!!)
                repository.clearMatchId()
                onPartidaEmpezada()
            }
        }
    }
}
