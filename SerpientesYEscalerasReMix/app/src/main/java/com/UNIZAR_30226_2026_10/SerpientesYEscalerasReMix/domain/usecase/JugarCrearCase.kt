package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class Lobby(
    val id: String,
    val hostEmail: String,
    val players: List<Player>
)

data class Player(
    val email: String,
    val username: String,
    val isReady: Boolean,
    val isBot: Boolean = false,
    val deckName: String? = null
)

class JugarCrearCase(private val userEmail: StateFlow<String>) { // TODO añadir remote

    val playersEjemplo = listOf(
        Player(
            email = "paco@gmail.com",
            username = "Paco_Lider",
            isReady = true,      // El líder suele estar listo por defecto
            isBot = false,
            deckName = "Mazo Clásico"
        ),
        Player(
            email = "ana@gmail.com",
            username = "Ana_Player",
            isReady = false,      // Esta jugadora ya ha pulsado el botón de Listo
            isBot = false,
            deckName = "Mazo Dragones"
        ),
        Player(
            email = "bot_1@system.com",
            username = "Bot_Agresivo",
            isReady = true,      // Los bots siempre están listos
            isBot = true,
            deckName = "Mazo Básico"
        )
    )


    val LobbyEjemplo1 = Lobby(
        id = "lobby_777_abc",
        hostEmail = "paco@gmail.com", // Coincide con el email de Paco_Lider
        players = playersEjemplo
    )

    val LobbyEjemplo2 = Lobby(
        id = "lobby_778_abc",
        hostEmail = "ana@gmail.com", // Coincide con el email de Paco_Lider
        players = playersEjemplo
    )

    private val _currentLobby = MutableStateFlow<Lobby?>(null)
    val currentLobby: StateFlow<Lobby?> = _currentLobby.asStateFlow()

    suspend fun crearLobby(username: String){
        // TODO Llamada a la API
        // POST /api/lobbies
        // _currentLobby.value = remote.createLobby(userEmail.value, username)

        _currentLobby.value = LobbyEjemplo1
    }
    
    suspend fun obtenerEstadoLobby(lobbyId: String){
        // TODO Llamada a la API
        // GET /api/lobbies/:lobby-id
        // _currentLobby.value = remote.getLobbyInfo(lobbyId)

        _currentLobby.value = LobbyEjemplo2
    }


    suspend fun añadirBot(lobbyId: String) {
        // TODO Llamada a la API
        // POST /api/lobbies/:lobby-id/bots
        //  remote.addBot(lobbyId, requestedBy = userEmail.value)
    }


    suspend fun unirseALobby(lobbyId: String, username: String): Boolean {
        // TODO Llamada a la API
        // POST /api/lobbies/:lobby-id/players
        // return remote.joinLobby(lobbyId, userEmail.value, username)

        return true
    }


    suspend fun seleccionarMazo(lobbyId: String, deckName: String): Boolean {
        // TODO Llamada a la API
        // PUT /api/lobbies/:lobby-id/players/:email/deck
        // return remote.setPlayerDeck(lobbyId, userEmail.value, deckName)

        return true
    }

    suspend fun cambiarEstadoListo(lobbyId: String, isReady: Boolean): Boolean {
        // TODO Llamada a la API
        // PUT /api/lobbies/:lobby-id/players/:email/ready
        // return remote.setPlayerReady(lobbyId, userEmail.value, isReady)

        return true
    }


    suspend fun abandonarExpulsar(lobbyId: String, emailAEliminar: String): Boolean {
        // TODO Llamada a la API
        // DELETE /api/lobbies/:lobby-id/players/:email
        /*return remote.removePlayer(
            lobbyId = lobbyId,
            playerEmail = emailAEliminar,
            requestedBy = userEmail.value
        )*/

        return true
    }
}