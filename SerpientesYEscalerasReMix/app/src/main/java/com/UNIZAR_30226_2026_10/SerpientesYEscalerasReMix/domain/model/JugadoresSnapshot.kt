package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model

import androidx.compose.ui.graphics.Color

// TODO eliminar JugadorEstadoSchema la informacion perteneciente a las fichas (API -- repo, aqui no debe de estar)

enum class FaseJuego {
    Cartas, Movimiento
}

data class EfectoActivo(
    val tipo: String,
    val duracion: Int
)

data class JugadorEstado(
    val esLider: Boolean = false,
    val email: String,
    val nombre: String,
    val icono: Int,
    val fase: FaseJuego,
    val ultimaTirada: Int? = null,
    val mazo: String,
    val mano: List<String>,        // Máximo 4
    val efectosActivos: List<EfectoActivo>,
    val color: Color    // Asignado en repository, se podra cambiar por un icono de la ficha
)

data class JugadoresSnapshot(
    val turno: Int,
    val ronda: Int,
    val jugadores: List<JugadorEstado>
)
