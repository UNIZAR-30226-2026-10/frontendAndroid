package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Amigo

class AmigosCase { // TODO añadir remote amigos

    suspend fun buscarAmigos(searchText: String): List<Amigo> { // TODO poner en dev la clase correspondiente
        // TODO llamada a la API, ahora siempre dev unos amigo por def sin importar el nombre

        val listaAmigosEjemploBuscado = listOf(
            Amigo("Buscado1", "online", true),
            Amigo("Buscado2", "desconectado", false)
        )

        return  listaAmigosEjemploBuscado

    }

}