package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model

class Movimiento (
    val fichaId: Int,
    val casillaId: Int,
    val esBifurcacion: Boolean,
    val pasosRestantes: Int = 0
)