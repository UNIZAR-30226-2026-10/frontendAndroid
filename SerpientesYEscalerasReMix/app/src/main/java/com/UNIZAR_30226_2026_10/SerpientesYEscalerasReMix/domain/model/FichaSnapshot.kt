package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model

import androidx.compose.ui.graphics.Color

class FichaSnapshot (
    val idJugador: String,
    val id: Int,      // 1..3
    val casilla: Int, // 0..100
    val meta: Boolean = false,
    val esUsuario: Boolean = false,
    val idImg: String,
    val color: Color
)
