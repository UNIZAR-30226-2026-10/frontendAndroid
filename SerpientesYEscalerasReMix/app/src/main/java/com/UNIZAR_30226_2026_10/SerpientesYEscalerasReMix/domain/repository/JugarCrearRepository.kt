package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Lobby
import kotlinx.coroutines.flow.StateFlow

interface JugarCrearRepository {
    val lobbyId: StateFlow<String>
    val lobbyActual: StateFlow<Lobby>

    suspend fun setLobbyId(lobbyId: String)
    suspend fun fetchLobby()
    suspend fun crearLobby(email: String, username: String) // Actualiza lobbyId
    suspend fun anadirBot(requestedBy: String)
    suspend fun cambiarPreparado(email: String, listo: Boolean)
    suspend fun seleccionarMazo(email: String, mazo: String)
    suspend fun seleccionarTablero(email: String, tablero: String)
    suspend fun abandonarExpulsar(email: String, emailTarget: String)
    suspend fun empezarPartida(): String // Devuelve matchId
}