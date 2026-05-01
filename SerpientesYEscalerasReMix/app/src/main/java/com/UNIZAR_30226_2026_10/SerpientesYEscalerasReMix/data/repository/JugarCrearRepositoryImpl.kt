package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.repository

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.JugadorLobby
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Lobby
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.JugarCrearRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class JugarCrearRepositoryImpl : JugarCrearRepository { // TODO añadir JugarCrearRepositoryImpl(private val api: ApiService) o lo que sea

    private val _lobbyId = MutableStateFlow("")
    override val lobbyId: StateFlow<String> = _lobbyId.asStateFlow()

    private val _lobbyActual = MutableStateFlow(Lobby("", "", emptyList()))
    override val lobbyActual: StateFlow<Lobby> = _lobbyActual.asStateFlow()

    override suspend fun setLobbyId(lobbyId: String) {
        _lobbyId.value = lobbyId
    }

    override suspend fun fetchLobby() {
        // TODO Llamar a GET /api/lobbies/:lobbyId
        // TODO Mapear DTO a Domain Model
        // TODO _lobbyActual.value = ...

        // Fake data para visualización
        _lobbyActual.value = Lobby(
            id = _lobbyId.value,
            hostEmail = "host@example.com",
            players = listOf(
                JugadorLobby("host@example.com", "HostUser", isReady = true),
                JugadorLobby("user2@example.com", "GuestUser", isReady = false),
                null,
                null
            )
        )
    }

    override suspend fun crearLobby(email: String, username: String) {
        // TODO Llamar a POST /api/lobbies
        // TODO _lobbyId.value = response.lobbyId
        // TODO fetchLobby() o actualizar localmente

        _lobbyId.value = "lobby_fake_123"
        fetchLobby()
    }

    override suspend fun anadirBot(requestedBy: String) {
        // TODO Llamar a POST /api/lobbies/:lobbyId/bots
    }

    override suspend fun cambiarPreparado(email: String, listo: Boolean) {
        // TODO Llamar a PUT /api/lobbies/:lobbyId/players/:email/ready
    }

    override suspend fun seleccionarMazo(email: String, mazo: String) {
        // TODO Llamar a PUT /api/lobbies/:lobbyId/players/:email/deck
    }

    override suspend fun seleccionarTablero(email: String, tablero: String) {
        // TODO Llamar a PUT /api/lobbies/:lobbyId/board
    }

    override suspend fun abandonarExpulsar(email: String, emailTarget: String) {
        // TODO Llamar a DELETE /api/lobbies/:lobbyId/players/:emailTarget
    }

    override suspend fun empezarPartida(): String {
        // TODO Llamar a POST /api/lobbies/:lobbyId/start
        // Devuelve el matchId de la partida creada
        return "match_fake_456"
    }
}
