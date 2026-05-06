package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Usuario
import kotlinx.coroutines.flow.StateFlow

interface AmigosRepository {
    val amigos: StateFlow<List<Usuario>>

    suspend fun obtenerAmigos(email: String)
    suspend fun anadirAmigo(nombre: String, email: String): Boolean
    suspend fun eliminarAmigo(nombre: String, email: String): Boolean
    suspend fun invitarAmigoLobby(nombre: String, myUsername: String, lobbyId: String): Boolean

    suspend fun obtenerInvitaciones(email: String)
    suspend fun responderInvitacion(lobbyId: String, inviteFrom: String, aceptar: Boolean, myUsername: String): Boolean
}
