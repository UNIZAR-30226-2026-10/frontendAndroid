package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

data class Usuario( // TODO mover a donde sea pertienente
    val nombre: String,
    val estadoTexto: String, // "online", "te ha invitado", etc.
    val estaOnline: Boolean,
    val esAmigo: Boolean = false,
    val haInvitado: Boolean = false
)

class AmigosCase { // TODO añadir remote amigos

    suspend fun obtenerAmigos(): List<Usuario> {
        // TODO llamada a la API, ahora siempre dev unos amigo por def sin importar el nombre
        val listaAmigosEjemplo = listOf(
            Usuario("EscaladorMaestro", "online", true, true),
            Usuario("ZigZagKing", "te ha invitado", true, true, haInvitado = true),
            Usuario("Colmillo Veloz", "online", true, true),
            Usuario("Escalera77", "desconectado", false, true)
        )

        return listaAmigosEjemplo
    }

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