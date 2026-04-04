package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import kotlinx.coroutines.flow.StateFlow

data class Partida(
    val nombre: String,
    val fecha: String,
    val turno: Int,
    val jugadores: String,
    val id: Int
)

class JugarContinuarCase(private val email: StateFlow<String>, private val username: StateFlow<String>) { // TODO añadir remote amigos

    suspend fun obtenerPartidas(): List<Partida> { // TODO poner en dev la clase correspondiente
        // TODO llamada a la API, ahora siempre dev unos amigo por def sin importar el nombre
        // GET /api/users/:email/matches

        val partidasDeEjemplo = listOf(
            Partida(
                nombre = "Partida de Escalador Maestro",
                fecha = "2 / 1 / 2026",
                turno = 46,
                jugadores = "Escalador Maestro, ZigZagKing, Colmillo Veloz y Escalera77",
                1
            ),
            Partida(
                nombre = "Duelo de Serpientes",
                fecha = "3 / 1 / 2026",
                turno = 3,
                jugadores = "Escalador Maestro, ZigZagKing, Colmillo Veloz y Escalera77",
                2
            ),
            Partida(
                nombre = "Torneo Nocturno",
                fecha = "5 / 1 / 2026",
                turno = 12,
                jugadores = "PythonPro, Anaconda99 y Escalador Maestro",
                3
            ),
            Partida(
                nombre = "Partida Rápida",
                fecha = "09 / 03 / 2026",
                turno = 1,
                jugadores = "Solo contra la IA",
                4
            )
        )

        return partidasDeEjemplo
    }
}
