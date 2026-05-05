package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Usuario

interface AmigosRepository {
    suspend fun obtenerAmigos(): List<Usuario>
    suspend fun buscarAmigos(query: String): List<Usuario>
    suspend fun añadirAmigo(nombre: String): Boolean
    suspend fun eliminarAmigo(nombre: String): Boolean
    suspend fun invitarAmigoLobby(nombre: String): Boolean
    suspend fun getAmigoLobby(nombre: String): String? // devuelve el lobbyId
}
