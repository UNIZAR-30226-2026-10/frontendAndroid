package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Jugar_Amigos.Usuario

class AmigosCase { // TODO añadir remote amigos

    suspend fun buscarAmigos(searchText: String): List<Usuario> { // TODO poner en dev la clase correspondiente
        // TODO llamada a la API, ahora siempre dev unos amigo por def sin importar el nombre

        val listaAmigosEjemploBuscado = listOf(
            Usuario("Buscado1", "online", true),
            Usuario("Buscado2", "desconectado", false)
        )

        return listaAmigosEjemploBuscado

    }

    suspend fun añadirAmigo(amigoNombre: String) {
        // TODO llamar a la API
    }

    suspend fun eliminarAmigo(amigoNombre: String) {
        // TODO llamar a la API
    }

    suspend fun invitarAmigoLobby(amigoNombre: String) {
        // TODO llamar a la API
    }

    suspend fun unirseAmigoLobby(amigoNombre: String) : Boolean {
        // TODO llamar a la API
        return true
    }

}