package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model


// TODO recuperar esta informacion respecto a FichaSchema y JugadorEstadoSchema recibidos de la API -- en el repository

class FichaSnapshot (
    val idJugador: String,
    val id: Int,      // 1..3
    val casilla: Int, // 0..100
    val meta: Boolean = false,
    val esUsuario: Boolean = false,
    val idImg: Int? = null // null al ser construido por repository, pero inicializada SIEMPRE en usecase
)
