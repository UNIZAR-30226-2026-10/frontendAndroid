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

class JugarCrearCase(
    private val email: StateFlow<String>,
    private val username: StateFlow<String>,
    private val lobbyId: MutableStateFlow<String>
) { // TODO añadir remote

    val playersEjemplo2: List<JugadorLobby?> = listOf(
        JugadorLobby(
            email = "paco@gmail.com",
            username = "ZigZagKing",
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
        null
    )

    val LobbyEjemplo2 = Lobby(
        id = "1",
        hostEmail = "paco@gmail.com", // Coincide con el email de Paco_Lider
        players = playersEjemplo2
    )

    val playersEjemplo1: List<JugadorLobby?>
        get() = listOf(
            JugadorLobby(
                email = "ibai@gmail.com",
                username = "Ibai_llanos",
                isReady = true,
                isBot = false,
                deckName = "Mazo Clásico"
            ),
            JugadorLobby(
                email = email.value,
                username = username.value,
                isReady = false,
                isBot = false,
                deckName = "Mazo Dragones"
            ),
            null,
            null
        )

    val LobbyEjemplo1
        get() = Lobby(
            id = "2",
            hostEmail = email.value,
            players = playersEjemplo1
        )

    private val _currentLobby = MutableStateFlow<Lobby?>(null)
    val currentLobby: StateFlow<Lobby?> = _currentLobby.asStateFlow()

    suspend fun crearLobby() {
        // TODO Llamada a la API
        // POST /api/lobbies
        // _currentLobby.value = remote.createLobby(email.value, username.value)

        lobbyId.value = "1"
    }

    suspend fun obtenerEstadoLobby() {
        // TODO Llamada a la API
        // GET /api/lobbies/:lobby-id
        // _currentLobby.value = remote.getLobbyInfo(lobbyId.value)

        _currentLobby.value = if (lobbyId.value == "1") LobbyEjemplo1 else LobbyEjemplo2
    }


    suspend fun anadirBot() {
        // TODO Llamada a la API y quizas mostrar snackbar de error si justo alguien se une
        // POST /api/lobbies/:lobby-id/bots
        //  val exito = remote.addBot(lobbyId, requestedBy = userEmail.value)

        val exito = true

        if (exito) { // TODO borrar (solo sirve para ver si se llama bien a la funcion, deberia de añadirse y en unos segundos desaparecer el bot)
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


    suspend fun unirseALobby(): Boolean {
        // TODO Llamada a la API
        // POST /api/lobbies/:lobby-id/players
        // return remote.joinLobby(lobbyId.value, userEmail.value, username.value)

        return true
    }


    suspend fun seleccionarMazo(deckName: String): Boolean {
        // TODO Llamada a la API
        // PUT /api/lobbies/:lobby-id/players/:email/deck
        // return remote.setPlayerDeck(lobbyId.value, userEmail.value, deckName)

        return true
    }

    suspend fun cambiarEstadoPreparado(preparado: Boolean){
        // TODO Llamada a la API
        // PUT /api/lobbies/:lobby-id/players/:email/ready
        // remote.setPlayerReady(lobbyId.value, email.value, isReady)
    }


    suspend fun abandonarExpulsar(emailAEliminar: String) {
        // TODO Llamada a la API
        // DELETE /api/lobbies/:lobby-id/players/:email
        // con lobbyId.value, emailAEliminar (cabecera) y tu mail email.value en cuerpo (reqBy)
    }
}