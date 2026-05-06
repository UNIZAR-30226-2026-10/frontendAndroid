package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.repository

import android.util.Log
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.ApiService
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.AceptarInvitacionRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.AmigoGetReply
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.InvitacionGetReply
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.PostInvitacionRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Usuario
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.AmigosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AmigosRepositoryImpl(private val api: ApiService) : AmigosRepository {

    private val _amigos = MutableStateFlow<List<Usuario>>(emptyList())
    override val amigos: StateFlow<List<Usuario>> = _amigos.asStateFlow()

    override suspend fun obtenerAmigos(email: String) {

        try {
            val response = api.getFriends(email)
            if (response.isSuccessful) {
                val nuevosAmigos = response.body()?.friends?.map { it.toDomain() } ?: emptyList()
                val actuales = _amigos.value
                val invitacionesActuales = actuales.filter { it.haInvitado }.associateBy { it.nombre }
                
                // Combinar amigos con sus invitaciones si existen
                _amigos.value = nuevosAmigos.map { amigo ->
                    invitacionesActuales[amigo.nombre]?.let { inv ->
                        amigo.copy(haInvitado = true, estadoTexto = inv.estadoTexto, lobbyInvitado = inv.lobbyInvitado)
                    } ?: amigo
                } + invitacionesActuales.filter { it.key !in nuevosAmigos.map { it.nombre } }.values
            } else {
                Log.e("AmigosRepo", "Error al obtener amigos: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e("AmigosRepo", "Excepción al obtener amigos", e)
        }
    }

    override suspend fun anadirAmigo(nombre: String, email: String): Boolean {

        return try {
            val response = api.addFriend(email, nombre)
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("AmigosRepo", "Excepción al añadir amigo", e)
            false
        }
    }

    override suspend fun eliminarAmigo(nombre: String, email: String): Boolean {

        return try {
            val response = api.removeFriend(email, nombre, mapOf("friendUsername" to nombre))
            if (response.isSuccessful) {
                true
            } else false
        } catch (e: Exception) {
            Log.e("AmigosRepo", "Excepción al eliminar amigo", e)
            false
        }
    }

    override suspend fun invitarAmigoLobby(nombre: String, myUsername: String, lobbyId: String): Boolean {

        return try {
            val response = api.sendInvitation(
                lobbyId,
                PostInvitacionRequest(inviteFrom = myUsername, inviteFor = nombre)
            )
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("AmigosRepo", "Excepción al invitar amigo", e)
            false
        }
    }

    override suspend fun obtenerInvitaciones(username: String) {

        try {
            val response = api.getInvitations(username)
            if (response.isSuccessful) {
                val nuevasInvitaciones = response.body()?.invites?.map { it.toDomain() } ?: emptyList()
                val currentList = _amigos.value
                val invitacionesMap = nuevasInvitaciones.associateBy { it.nombre }
                
                // Actualizar la lista de amigos con las invitaciones
                val listaActualizada = currentList.map { user ->
                    invitacionesMap[user.nombre]?.let { inv ->
                        user.copy(haInvitado = true, estadoTexto = inv.estadoTexto, lobbyInvitado = inv.lobbyInvitado)
                    } ?: user.copy(haInvitado = false, estadoTexto = "", lobbyInvitado = "")
                }.toMutableList()

                // Añadir personas que han invitado pero no estaban en la lista (Evitar errores)
                val nombresExistentes = listaActualizada.map { it.nombre }.toSet()
                nuevasInvitaciones.forEach { inv ->
                    if (inv.nombre !in nombresExistentes) {
                        listaActualizada.add(inv)
                    }
                }

                _amigos.value = listaActualizada
            } else {
                Log.e("AmigosRepo", "Error al obtener invitaciones: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e("AmigosRepo", "Excepción al obtener invitaciones", e)
        }
    }

    override suspend fun responderInvitacion(lobbyId: String, inviteFrom: String, aceptar: Boolean, myUsername: String): Boolean {

        return try {
            val response = api.respondInvitation(
                lobbyId,
                AceptarInvitacionRequest(inviteFor = myUsername, inviteFrom = inviteFrom, accept = aceptar)
            )
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("AmigosRepo", "Excepción al responder invitación", e)
            false
        }
    }

    private fun AmigoGetReply.toDomain() = Usuario(
        nombre = nombre
    )

    private fun InvitacionGetReply.toDomain() = Usuario(
        nombre = inviteFrom,
        estadoTexto = "te ha invitado",
        haInvitado = true,
        lobbyInvitado = partidaID
    )
}
