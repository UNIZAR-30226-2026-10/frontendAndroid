package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model

import androidx.compose.ui.graphics.Color


data class Jugador (
    val nombreJugador: String?,
    val esTurno: Boolean?,
    val esLider: Boolean?,
    val iconoJugador: Int,
    val colorFichas: Color
)