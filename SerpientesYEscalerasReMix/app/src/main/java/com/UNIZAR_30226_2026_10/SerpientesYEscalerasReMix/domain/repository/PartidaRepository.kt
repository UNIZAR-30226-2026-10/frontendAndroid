package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Carta
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.FichaSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.JugadoresSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Movimiento
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.MsgChat
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.TableroSnapshot
import kotlinx.coroutines.flow.StateFlow

interface PartidaRepository {
    // Flujos de datos (Single State of Truth) seran cambiados con fetchEstadoCompleto
    val matchId: StateFlow<String>
    val tablero: StateFlow<TableroSnapshot>
    val fichas: StateFlow<List<FichaSnapshot>>
    val jugadores: StateFlow<JugadoresSnapshot>
    val mano: StateFlow<List<Carta?>>
    val chat: StateFlow<List<MsgChat>>
    val ganador: StateFlow<String>


    // Modificar Flujos desde el exterior
    suspend fun setMatchId(matchId: String)
    
    suspend fun cleanPartidaState()

    // Acciones respecto a paquete data
    suspend fun fetchEstadoCompleto(matchId: String, username: String)

    // Devuelve las casillas posibles y la puntuacion del dado
    suspend fun lanzarDado(matchId: String, username: String): Pair<Int, List<Movimiento>>

    suspend fun confirmarMovimiento(matchId: String, username: String, fichaId: Int, destinoId: Int, pasosRestantes: Int?)
    suspend fun jugarCarta(matchId: String, username: String, cartaId: String, target: String?, inicio: Int?, fin: Int?)
    suspend fun recibirChat(matchId: String, username: String)
    suspend fun enviarMensaje(matchId: String, mensaje: MsgChat)
}