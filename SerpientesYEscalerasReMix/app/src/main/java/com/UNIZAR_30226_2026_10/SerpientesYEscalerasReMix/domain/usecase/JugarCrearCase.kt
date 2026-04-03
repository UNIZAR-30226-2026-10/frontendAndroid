package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class Lobby(
    val id: String,
    val hostEmail: String,
    val players: List<JugadorLobby?>
)

data class JugadorLobby(
    val email: String,
    val username: String,
    val profileIcon: String = "default",
    val isReady: Boolean,
    val isBot: Boolean = false,
    val deckName: String? = null
)

class JugarCrearCase(private val userEmail: StateFlow<String>, private val username: StateFlow<String>) { // TODO añadir remote

    val playersEjemplo: List<JugadorLobby?> = listOf(
        JugadorLobby(
            email = "paco@gmail.com",
            username = "Paco_Lider",
            isReady = true,
            isBot = false,
            deckName = "Mazo Clásico"
        ),
        JugadorLobby(
            email = "ana@gmail.com",
            username = "Ana_Player",
            isReady = false,
            isBot = false,
            deckName = "Mazo Dragones"
        ),
        JugadorLobby(
            email = "bot_1@system.com",
            username = "Bot_Agresivo",
            isReady = true,
            isBot = true,
            deckName = "Mazo Básico"
        ),
        null // Cuarto hueco vacío
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
    
    suspend fun obtenerEstadoLobby(lobbyId: String?){
        // TODO Llamada a la API
        // GET /api/lobbies/:lobby-id
        // _currentLobby.value = remote.getLobbyInfo(lobbyId)

        _currentLobby.value = LobbyEjemplo2
    }


    suspend fun anadirBot(lobbyId: String?) {
        // TODO Llamada a la API
        // POST /api/lobbies/:lobby-id/bots
        //  val exito = remote.addBot(lobbyId, requestedBy = userEmail.value)

        val exito = true

        if (exito) {
            val lobbyActual = _currentLobby.value ?: return // salir en caso de que no haya un lobby (no deberia de pasar)

            val indexPrimerHueco = lobbyActual.players.indexOfFirst { it == null }

            if (indexPrimerHueco != -1) {
                // Crear el nuevo bot
                val nuevoBot = JugadorLobby( // TODO Cambiar
                    email = "bot_${System.currentTimeMillis()}@system.com",
                    username = "Bot_${indexPrimerHueco + 1}",
                    isReady = true, // Los bots suelen estar listos por defecto
                    isBot = true,   // Campo obligatorio en true
                    deckName = "Mazo Básico"
                )

                val nuevaLista = lobbyActual.players.toMutableList().apply {
                    set(indexPrimerHueco, nuevoBot)
                }
                _currentLobby.value = lobbyActual.copy(players = nuevaLista)
            }
        }
    }


    suspend fun unirseALobby(lobbyId: String?, username: String): Boolean {
        // TODO Llamada a la API
        // POST /api/lobbies/:lobby-id/players
        // return remote.joinLobby(lobbyId, userEmail.value, username)

        return true
    }


    suspend fun seleccionarMazo(lobbyId: String?, deckName: String): Boolean {
        // TODO Llamada a la API
        // PUT /api/lobbies/:lobby-id/players/:email/deck
        // return remote.setPlayerDeck(lobbyId, userEmail.value, deckName)

        return true
    }

    suspend fun cambiarEstadoListo(lobbyId: String?, isReady: Boolean): Boolean {
        // TODO Llamada a la API
        // PUT /api/lobbies/:lobby-id/players/:email/ready
        // return remote.setPlayerReady(lobbyId, userEmail.value, isReady)

        return true
    }


    suspend fun abandonarExpulsar(lobbyId: String?, emailAEliminar: String?) {
        // TODO Llamada a la API
        // DELETE /api/lobbies/:lobby-id/players/:email
        /*val exito = remote.removePlayer(
            lobbyId = lobbyId,
            playerEmail = emailAEliminar,
            requestedBy = userEmail.value
        )*/

        val exito = true // TODO eliminar

        if (exito) {
            val lobbyActual = _currentLobby.value

            if (lobbyActual != null) {
                // Si el que se va es el líder, el lobby se destruye
                if (emailAEliminar == lobbyActual.hostEmail) {
                    _currentLobby.value = null
                    crearLobby(username.value)
                // Si es un jugador normal, filtramos la lista
                } else {
                    val nuevaListaJugadores = lobbyActual.players.filter { it?.email != emailAEliminar }
                    _currentLobby.value = lobbyActual.copy(players = nuevaListaJugadores)
                }
            }
        }
    }
}