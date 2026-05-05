package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.repository

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.ApiService
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Usuario
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.AmigosRepository

class AmigosRepositoryImpl(private val apiService: ApiService) : AmigosRepository {

    override suspend fun obtenerAmigos(): List<Usuario> {
        // TODO: Implementar llamada a la API para obtener la lista de amigos
        return emptyList()
    }

    override suspend fun buscarAmigos(query: String): List<Usuario> {
        // TODO: Implementar llamada a la API para buscar usuarios por nombre
        return emptyList()
    }

    override suspend fun añadirAmigo(nombre: String): Boolean {
        // TODO: Implementar llamada a la API para añadir un amigo
        return false
    }

    override suspend fun eliminarAmigo(nombre: String): Boolean {
        // TODO: Implementar llamada a la API para eliminar un amigo
        return false
    }

    override suspend fun invitarAmigoLobby(nombre: String): Boolean {
        // TODO: Implementar llamada a la API para invitar a un amigo al lobby actual
        return false
    }

    override suspend fun getAmigoLobby(nombre: String): String? {
        // TODO: Implementar llamada a la API para obtener el ID del lobby de un amigo
        return null
    }
}
