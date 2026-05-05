package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.repository

import android.util.Log
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.ApiClient
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.*
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.JugadorLobby
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Lobby
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.JugarCrearRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class JugarCrearRepositoryImpl : JugarCrearRepository {

    private val api = ApiClient.apiService

    private val _lobbyId = MutableStateFlow("")
    override val lobbyId: StateFlow<String> = _lobbyId.asStateFlow()

    private val _lobbyActual = MutableStateFlow(Lobby("", "", emptyList()))
    override val lobbyActual: StateFlow<Lobby> = _lobbyActual.asStateFlow()

    override suspend fun setLobbyId(lobbyId: String) {
        _lobbyId.value = lobbyId
    }

    override suspend fun fetchLobby() {
        if (_lobbyId.value.isEmpty()) return
        try {
            val response = api.getLobby(_lobbyId.value)
            if (response.isSuccessful) {
                response.body()?.let { reply ->
                    _lobbyActual.value = reply.toDomain()
                }
            } else {
                Log.e("API_ERROR", "Error fetching lobby: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e("API_ERROR", "Exception fetching lobby", e)
        }
    }

    override suspend fun fetchLobbyByPlayer(username: String) {
        try {
            val response = api.getLobbyByPlayer(username)
            if (response.isSuccessful) {
                response.body()?.let { reply ->
                    _lobbyId.value = reply.idLobby
                    _lobbyActual.value = reply.toDomain()
                }
            }
        } catch (e: Exception) {
            Log.e("API_ERROR", "Exception fetching lobby by player", e)
        }
    }

    override suspend fun crearLobby(username: String) {
        try {
            val response = api.createLobby(CrearLobbyRequest(username))
            if (response.isSuccessful) {
                response.body()?.let { reply ->
                    _lobbyId.value = reply.idLobby
                    _lobbyActual.value = reply.toDomain()
                }
            }
        } catch (e: Exception) {
            Log.e("API_ERROR", "Exception creating lobby", e)
        }
    }

    override suspend fun anadirBot(requestedBy: String) {
        try {
            api.addBot(_lobbyId.value, AnadirBotRequest(requestedBy))
            fetchLobby()
        } catch (e: Exception) {
            Log.e("API_ERROR", "Exception adding bot", e)
        }
    }

    override suspend fun cambiarPreparado(username: String, listo: Boolean) {
        try {
            api.setReady(_lobbyId.value, username, mapOf("ready" to listo))
            fetchLobby()
        } catch (e: Exception) {
            Log.e("API_ERROR", "Exception changing ready state", e)
        }
    }

    override suspend fun seleccionarMazo(username: String, mazo: String) {
        try {
            api.selectDeck(_lobbyId.value, username, SeleccionMazoRequest(mazo))
            fetchLobby()
        } catch (e: Exception) {
            Log.e("API_ERROR", "Exception selecting deck", e)
        }
    }

    override suspend fun seleccionarTablero(requestedBy: String, tablero: String) {
        try {
            api.setBoard(_lobbyId.value, mapOf("requested_by" to requestedBy, "board" to tablero))
            fetchLobby()
        } catch (e: Exception) {
            Log.e("API_ERROR", "Exception selecting board", e)
        }
    }

    override suspend fun abandonarExpulsar(requestedBy: String, targetUsername: String) {
        try {
            api.leaveOrExpel(_lobbyId.value, targetUsername, mapOf("requested_by" to requestedBy))
            if (requestedBy == targetUsername) {
                _lobbyId.value = ""
                _lobbyActual.value = Lobby("", "", emptyList())
            } else {
                fetchLobby()
            }
        } catch (e: Exception) {
            Log.e("API_ERROR", "Exception leaving/expelling", e)
        }
    }

    override suspend fun empezarPartida(): String {
        return try {
            val response = api.startMatch(_lobbyId.value)
            if (response.isSuccessful) {
                response.body()?.get("match_id") ?: ""
            } else {
                ""
            }
        } catch (e: Exception) {
            Log.e("API_ERROR", "Exception starting match", e)
            ""
        }
    }

    private fun LobbyReply.toDomain() = Lobby(
        id = idLobby,
        hostEmail = idCreador,
        players = jugadores.map { it.toDomain() }.let { list ->
            val mutable = list.toMutableList<JugadorLobby?>()
            while (mutable.size < 4) mutable.add(null)
            mutable
        }
    )

    private fun JugadoresLobbyReply.toDomain() = JugadorLobby(
        email = idJugador,
        username = nombre,
        profileIcon = icono,
        isReady = estaListo,
        isBot = esIA,
        deckName = nombreMazo
    )
}
