package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.repository

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.fakes.fakeFichasSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.fakes.fakeJugadoresSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.fakes.fakeManoCartas
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.fakes.fakeMsgChat
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.fakes.fakeTableroSnapshot
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
        // TODO _jugadores.value = ...
        // TODO _mano.value = ...

        _tablero.value = fakeTableroSnapshot
        _fichas.value = fakeFichasSnapshot
        _jugadores.value = fakeJugadoresSnapshot
        _mano.value = fakeManoCartas
    }

    override suspend fun lanzarDado(matchId: String, email: String): Pair<Int, List<Movimiento>> {
        // TODO POST /api/matches/:match_id/dice

        val fakeMovimientos = listOf<Movimiento>(
            Movimiento(
                fichaId = 1,
                casillaId = 2,
                esBifurcacion = false,
                pasosRestantes = 0
            ),

            Movimiento(
                fichaId = 2,
                casillaId = 2,
                esBifurcacion = false,
                pasosRestantes = 0
            ),

            Movimiento(
                fichaId = 3,
                casillaId = 8,
                esBifurcacion = false,
                pasosRestantes = 0
            )
        )

        return Pair(1, fakeMovimientos)
    }

    override suspend fun confirmarMovimiento (
        matchId: String,
        email: String,
        fichaId: Int,
        destinoId: Int,
        pasosRestantes: Int?
    ) : List<Movimiento> {
        // TODO POST /api/matches/:match_id/final-location con o sin pasos restantes + decision
        return emptyList()
    }

    override suspend fun jugarCarta(matchId: String, email: String, cartaId: String, target: String?, inicio: Int?, fin: Int?) {
        // TODO POST /api/matches/:match_id/cards
    }

    override suspend fun recibirChat(matchId: String) {
        // TODO _chat.value = ...
        _chat.value = fakeMsgChat
    }

    override suspend fun enviarMensaje(matchId: String, mensaje: MsgChat) {
        // TODO Implementar lógica de chat si existe endpoint o Socket
        val currentChat = _chat.value.toMutableList()
        currentChat.add(mensaje)
        _chat.value = currentChat
    }
}