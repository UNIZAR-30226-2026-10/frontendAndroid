package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.repository

import androidx.compose.ui.graphics.Color
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.ApiService
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.Casilla
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.ChatMsg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.ChatRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.JugarCartaRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.MovimientoRollDiceReply
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.PartidaReply
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.SnapshotTablero
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.UpdatePawnRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Calidad
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Carta
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.CasillaSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.EfectoActivo
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.FaseJuego
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.FichaSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.JugadorEstado
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.JugadoresSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Movimiento
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.MsgChat
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.TableroSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Tipo
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.TipoCasilla
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.PartidaRepository
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fichas_amarillas
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fichas_azules
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fichas_rojas
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fichas_verdes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PartidaRepositoryImpl(private val api: ApiService) : PartidaRepository {

    private val _matchId = MutableStateFlow("")
    private val _tablero = MutableStateFlow(TableroSnapshot(emptyList(), "default", "default"))
    private val _fichas = MutableStateFlow<List<FichaSnapshot>>(emptyList())
    private val _jugadores = MutableStateFlow(JugadoresSnapshot(0, 0, emptyList()))
    private val _mano = MutableStateFlow<List<Carta?>>(emptyList())
    private val _chat = MutableStateFlow<List<MsgChat>>(emptyList())

    override val matchId = _matchId.asStateFlow()
    override val tablero = _tablero.asStateFlow()
    override val fichas = _fichas.asStateFlow()
    override val jugadores = _jugadores.asStateFlow()
    override val mano = _mano.asStateFlow()
    override val chat = _chat.asStateFlow()

    private val playerColors = listOf(
        color_fichas_rojas, 
        color_fichas_azules, 
        color_fichas_verdes, 
        color_fichas_amarillas
    )

    override suspend fun setMatchId(matchId: String) {
        _matchId.value = matchId
    }

    override suspend fun fetchEstadoCompleto(matchId: String, username: String) {
        val response = api.getMatchStatus(matchId, username)
        if (response.isSuccessful) {
            response.body()?.let { updateState(it, username) }
        }
    }

    override suspend fun lanzarDado(matchId: String, username: String): Pair<Int, List<Movimiento>> {
        val response = api.rollDice(matchId, username)
        if (response.isSuccessful && response.body() != null) {
            val reply = response.body()!!
            updateState(reply.partida, username)
            val movimientos = reply.movimientos.map { it.toDomain() }
            val diceValue = reply.tirada.toIntOrNull() ?: 0
            return Pair(diceValue, movimientos)
        }
        return Pair(0, emptyList())
    }

    override suspend fun confirmarMovimiento(
        matchId: String,
        username: String,
        fichaId: Int,
        destinoId: Int,
        pasosRestantes: Int?
    ): List<Movimiento> {
        val response = api.updatePawn(matchId, username, UpdatePawnRequest(destinoId, fichaId, pasosRestantes))
        if (response.isSuccessful && response.body() != null) {
            val reply = response.body()!!
            updateState(reply, username)
            // Extraer movimientos permitidos para el usuario actual de la nueva snapshot
            val localPlayer = reply.snapshotJugadores.jugadores.find { it.username == username }
            return localPlayer?.movimientosPermitidos?.mapNotNull { 
                // Asumiendo que movimientosPermitidos viene como un objeto que podemos mapear o similar
                // Por ahora devolvemos vacío si no tenemos el DTO claro para 'Any'
                null 
            } ?: emptyList()
        }
        return emptyList()
    }

    override suspend fun jugarCarta(matchId: String, username: String, cartaId: String, target: String?, inicio: Int?, fin: Int?) {
        val response = api.playCard(matchId, username, JugarCartaRequest(cartaId, target, inicio, fin))
        if (response.isSuccessful && response.body() != null) {
            updateState(response.body()!!, username)
        }
    }

    override suspend fun recibirChat(matchId: String, username: String) {
        val response = api.getChat(matchId, username)
        if (response.isSuccessful && response.body() != null) {
            _chat.value = response.body()!!.chat.map { it.toDomain() }
        }
    }

    override suspend fun enviarMensaje(matchId: String, mensaje: MsgChat) {
        val response = api.sendChatMessage(matchId, mensaje.sender, ChatRequest(mensaje.message))
        if (response.isSuccessful && response.body() != null) {
            _chat.value = response.body()!!.chat.map { it.toDomain() }
        }
    }

    private fun updateState(reply: PartidaReply, myUsername: String) {
        _tablero.value = reply.snapshotTablero.toDomain()
        
        val snapshotJugadores = reply.snapshotJugadores.jugadores
        val partidaJugadores = reply.partidaJugadores

        val jugadoresMapeados = snapshotJugadores.mapIndexed { index, jug ->
            val infoExtra = partidaJugadores.find { it.nombre == jug.username }

            if (jug.username == myUsername) {
                _tablero.value.skinEscalera = infoExtra?.escaleraActualField ?: "default"
                _tablero.value.skinSerpiente = infoExtra?.serpienteActualField ?: "default"
            }

            JugadorEstado(
                username = jug.username,
                icono = infoExtra?.iconoActualField ?: "default",
                fase = if (jug.fase == "Cartas") FaseJuego.Cartas else FaseJuego.Movimiento,
                mazo = jug.mazo,
                mano = jug.mano,
                efectosActivos = jug.efectosActivos.map { EfectoActivo(it.toString(), 0) },
                color = playerColors.getOrElse(index) { Color.Gray }
            )
        }

        _jugadores.value = JugadoresSnapshot(
            turno = reply.snapshotJugadores.turnoActual,
            ronda = reply.snapshotJugadores.ronda,
            jugadores = jugadoresMapeados
        )
        
        val allFichas = mutableListOf<FichaSnapshot>()
        snapshotJugadores.forEach { jug ->
            jug.fichas.forEach { f ->
                allFichas.add(FichaSnapshot(
                    idJugador = jug.username,
                    id = f.id,
                    casilla = f.casilla,
                    meta = f.meta,
                    esUsuario = jug.username == myUsername
                ))
            }
        }
        _fichas.value = allFichas

        // Actualizar mano y posible info extra del jugador local
        val localSnapshot = snapshotJugadores.find { it.username == myUsername }
        _mano.value = localSnapshot?.mano?.map { 
            Carta(
                id = it.toIntOrNull(),
                nombre = it,
                descripcion = "Carta de mazo ${localSnapshot.mazo}",
                tipo = Tipo.Ofensiva,
                calidad = Calidad.Comun,
                imagen = 0
            )
        } ?: emptyList()

        _chat.value = reply.chat.map { it.toDomain() }
    }

    // --- Mapeos toDomain ---

    private fun SnapshotTablero.toDomain(): TableroSnapshot {
        return TableroSnapshot(
            casillas = this.casillas.map { it.toDomain() },
            skinEscalera = "default",
            skinSerpiente = "default"
        )
    }

    private fun Casilla.toDomain(): CasillaSnapshot {
        return CasillaSnapshot(
            esCurva = false,
            rotacion = 0,
            efecto = this.efecto,
            tipo = mapTipoCasilla(this.tipo),
            siguientes = this.siguientes,
            saltoA = this.saltoA
        )
    }

    private fun mapTipoCasilla(tipo: String?): TipoCasilla {
        return when (tipo) {
            "Normal" -> TipoCasilla.Normal
            "Escalera" -> TipoCasilla.Escalera
            "Serpiente" -> TipoCasilla.Serpiente
            "Bifurcacion" -> TipoCasilla.Bifurcacion
            "Meta" -> TipoCasilla.Meta
            else -> TipoCasilla.Normal
        }
    }

    private fun ChatMsg.toDomain(): MsgChat {
        return MsgChat(
            sender = this.mandadoPor,
            message = this.mensaje,
            isSystem = this.mandadoPor == "Sistema"
        )
    }

    private fun MovimientoRollDiceReply.toDomain(): Movimiento {
        return Movimiento(
            fichaId = this.fichaId.toIntOrNull() ?: 0,
            casillaId = this.casillaDestino.toIntOrNull() ?: 0,
            esBifurcacion = this.esBifurcacion.toBoolean(),
            pasosRestantes = this.pasosRestantes.toIntOrNull() ?: 0
        )
    }
}
