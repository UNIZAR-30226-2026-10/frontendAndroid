package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.repository

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Carta
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.FichaSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.JugadoresSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Movimiento
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.MsgChat
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.TableroSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.PartidaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PartidaRepositoryImpl :
    PartidaRepository { // TODO añadir PartidaRepositoryImpl(private val api: ApiService) o lo que sea

    private val _tablero = MutableStateFlow(TableroSnapshot(emptyList()))
    private val _fichas = MutableStateFlow<List<FichaSnapshot>>(emptyList())
    private val _jugadores = MutableStateFlow(JugadoresSnapshot(0, 0, emptyList()))
    private val _mano = MutableStateFlow<List<Carta?>>(emptyList())
    private val _chat = MutableStateFlow<List<MsgChat>>(emptyList())

    override val tablero = _tablero.asStateFlow()
    override val fichas = _fichas.asStateFlow()
    override val jugadores = _jugadores.asStateFlow()
    override val mano = _mano.asStateFlow()
    override val chat = _chat.asStateFlow()

    override suspend fun fetchEstadoCompleto(matchId: String, email: String) {
        // TODO Llamar a GET /api/matches/:match_id/board y /players
        // TODO Mapear DTOs a Domain Models
        // TODO _tablero.value = ...
        // TODO _fichas.value = ...
    }

    override suspend fun lanzarDado(matchId: String, email: String): Pair<Int, List<Movimiento>> {
        // TODO POST /api/matches/:match_id/dice
        return Pair(0, emptyList())
    }

    override suspend fun confirmarMovimiento(
        matchId: String,
        email: String,
        fichaId: Int,
        destinoId: Int
    ) {
        // TODO POST /api/matches/:match_id/final-location
    }

    override suspend fun jugarCarta(matchId: String, email: String, cartaId: Int, target: String?) {
        // TODO POST /api/matches/:match_id/cards
    }

    override suspend fun enviarMensaje(matchId: String, mensaje: MsgChat) {
        // TODO Implementar lógica de chat si existe endpoint o Socket
        val currentChat = _chat.value.toMutableList()
        currentChat.add(mensaje)
        _chat.value = currentChat
    }
}