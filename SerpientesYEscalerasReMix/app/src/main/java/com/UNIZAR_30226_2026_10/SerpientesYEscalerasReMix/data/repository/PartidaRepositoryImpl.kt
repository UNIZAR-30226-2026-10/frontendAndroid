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
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.TipoCasilla
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Tipo_Carta
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
    private val _ganador = MutableStateFlow("")
    private val _noqueado = MutableStateFlow(false)


    override val matchId = _matchId.asStateFlow()
    override val tablero = _tablero.asStateFlow()
    override val fichas = _fichas.asStateFlow()
    override val jugadores = _jugadores.asStateFlow()
    override val mano = _mano.asStateFlow()
    override val chat = _chat.asStateFlow()
    override val ganador = _ganador.asStateFlow()
    override val noqueado = _noqueado.asStateFlow()

    private val playerColors = listOf(
        color_fichas_rojas,
        color_fichas_azules,
        color_fichas_verdes,
        color_fichas_amarillas
    )

    override suspend fun setMatchId(matchId: String) {
        _matchId.value = matchId
    }

    override suspend fun cleanPartidaState() {
        _matchId.value = ""
        _tablero.value = TableroSnapshot(emptyList(), "default", "default")
        _fichas.value = emptyList()
        _jugadores.value = JugadoresSnapshot(0, 0, emptyList())
        _mano.value = emptyList()
        _chat.value = emptyList()
        _ganador.value = ""
    }

    override suspend fun fetchEstadoCompleto(matchId: String, username: String) {
        val response = api.getMatchStatus(matchId, username)
        if (response.isSuccessful) {
            response.body()?.let { updateState(it, username) }
        }
    }

    override suspend fun lanzarDado(
        matchId: String,
        username: String
    ): Pair<Int, List<Movimiento>> {
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
        pasosRestantes: Int?,
        esBifurcacion: Boolean
    ) : Boolean {
        val casillaDestino = _tablero.value.casillas.getOrNull(destinoId - 1)
        val destinoFinal = if (casillaDestino?.tipo == TipoCasilla.Serpiente && casillaDestino.saltoA != null) {
            casillaDestino.saltoA - 1
        } else {
            destinoId - 1
        }

        val response = api.updatePawn(
            matchId,
            username,
            UpdatePawnRequest(destinoFinal, fichaId, pasosRestantes)
        )
        if (response.isSuccessful && response.body() != null) {
            val reply = response.body()!!
            updateState(reply, username)
            val fichaActualizada = _fichas.value.find { it.id == fichaId && it.esUsuario }
            val enEscalera = fichaActualizada?.let {
                _tablero.value.casillas.getOrNull(it.casilla)?.tipo == TipoCasilla.Escalera
            } ?: false
            return esBifurcacion && enEscalera
        } else {
            return false
        }
    }

    override suspend fun jugarCarta(
        matchId: String,
        username: String,
        cartaId: String,
        target: String?,
        inicio: Int?,
        fin: Int?
    ) {
        val inicioAux =
            if(inicio == null) null
            else inicio - 1

        val finAux =
            if(fin == null) null
            else fin - 1

        val response =
            api.playCard(matchId, username, JugarCartaRequest(cartaId, target, inicioAux, finAux))
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

        val skinMap = mutableMapOf<String, Pair<String, Color>>()

        val jugadoresMapeados = snapshotJugadores.mapIndexed { index, jug ->
            val infoExtra = partidaJugadores.find { it.nombre == jug.username }

            if (jug.username == myUsername) {
                _tablero.value.skinEscalera = infoExtra?.escaleraActualField ?: "default"
                _tablero.value.skinSerpiente = infoExtra?.serpienteActualField ?: "default"
            }

            skinMap[jug.username] = Pair(
                infoExtra?.fichaActualField ?: "default",
                playerColors.getOrElse(index) { Color.Gray }
            )


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
                allFichas.add(
                    FichaSnapshot(
                        idJugador = jug.username,
                        id = f.id,
                        casilla = f.casilla,
                        meta = f.meta,
                        esUsuario = jug.username == myUsername,
                        idImg = skinMap[jug.username]?.first!!,
                        color = jugadoresMapeados.find { it.username == jug.username }?.color ?: Color.Gray
                    )
                )
            }
        }
        _fichas.value = allFichas

        // Actualizar mano y posible info extra del jugador local
        val localSnapshot = snapshotJugadores.find { it.username == myUsername }
        _mano.value = localSnapshot?.mano?.map { nombre ->
            Carta(
                id = nombre.toIntOrNull() ?: 0,
                nombre = nombre,
                descripcion = descripcionesCartas[nombre] ?: "Descripción no disponible",
                tipo = Tipo_Carta.Ofensiva,
                calidad = Calidad.Comun,
                imagen = 0
            )
        } ?: emptyList()

        _noqueado.value = localSnapshot?.efectosActivos?.any {
            val efecto = it as? Map<*, *>
            efecto?.get("resumenEfecto") == "Salto de turno"
        } ?: false

        _chat.value = reply.chat.map { it.toDomain() }

        _ganador.value = reply.ganador ?: ""
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
            esCurva = this.esCurva,
            rotacion = this.rotacion,
            efecto = this.efecto,
            tipo = mapTipoCasilla(this.tipo),
            siguientes = this.siguientes,
            saltoA = if(this.saltoA == null) null
                     else this.saltoA!! + 1
        )
    }

    private fun mapTipoCasilla(tipo: String?): TipoCasilla {
        return when (tipo) {
            "Normal" -> TipoCasilla.Normal
            "Escalera" -> TipoCasilla.Escalera
            "Serpiente" -> TipoCasilla.Serpiente
            "Bifurcacion" -> TipoCasilla.Bifurcacion
            "Meta" -> TipoCasilla.Meta
            "Vacía" -> TipoCasilla.Vacio
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
            casillaId = (this.casillaDestino.toIntOrNull() ?: 0) + 1,
            esBifurcacion = this.esBifurcacion.toBoolean(),
            pasosRestantes = this.pasosRestantes?.toIntOrNull() ?: 0
        )
    }

    val descripcionesCartas = mapOf(
        "Exceso de medios" to "Tiras 2 dados",
        "Moises" to "Te saltas un bloqueo",
        "Wild Frank" to "Pones una serpiente donde quieras",
        "Carpintero" to "Pones una escalera donde quieras",
        "Dia de la marmota" to "Cambias la casilla para que quien caiga se mueva 4 casillas atrás",
        "Salto de longitud" to "Cambias la casilla para que quien caiga se mueva 4 casillas adelante",
        "Robo de identidad" to "Cambias la posicion de una de tus fichas por otra al azar",
        "Mal de ojo" to "Le restas a un jugador 3 en su próxima tirada",
        "Antidoto" to "La próxima serpiente en la que caigas no te hará bajar",
        "Pickpocket" to "Robas una carta al azar a otro jugador",
        "Dado envenenado" to "El rival solo puede tirar dados de 1-3 en su próximo turno",
        "Dado dorado" to "Solo podrás sacar entre 4-6 en tu próxima tirada",
        "Serpiente en tu bota" to "Creas una casilla que impide al jugador que caiga en ella tirar dados en su próximo turno",
        "Parca" to "Mandas una ficha al azar al inicio del tablero",
        "Cambiar de idea" to "Descarta todas las cartas de tu mano y roba nuevas hasta llenar tu mano",
        "Agujero de serpiente" to "Crea una casilla que te teletransporta a una casilla aleatoria del tablero al caer en ella",
        "Bolsillo roto" to "Le quitas todas las cartas a un jugador y solo podrá robar 1 carta",
        "Compañerismo obligado" to "Teletransporta a tu ficha más atrás a la posición de una ficha aliada más avanzada",
        "Coleccionista" to "Roba dos cartas en tu próximo turno",
        "Noqueo" to "Cancela el próximo turno de un rival"
    )

}
