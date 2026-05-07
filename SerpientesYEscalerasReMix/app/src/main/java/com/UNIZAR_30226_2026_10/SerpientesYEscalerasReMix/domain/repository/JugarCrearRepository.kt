package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Lobby
import kotlinx.coroutines.flow.StateFlow

interface JugarCrearRepository {
    val lobbyId: StateFlow<String>
    val lobbyActual: StateFlow<Lobby>

    suspend fun setLobbyId(lobbyId: String)
    suspend fun fetchLobby()
    suspend fun fetchLobbyByPlayer(username: String)
    suspend fun crearLobby(username: String) // Actualiza lobbyId
    suspend fun anadirBot(requestedBy: String)
    suspend fun cambiarPreparado(username: String, listo: Boolean)
    suspend fun seleccionarMazo(username: String, mazo: String)
    suspend fun seleccionarTablero(requestedBy: String, tablero: String)
    suspend fun abandonarExpulsar(requestedBy: String, targetUsername: String)
    suspend fun empezarPartida(): String // Devuelve matchId
    suspend fun getBoards(): List<String>
}
