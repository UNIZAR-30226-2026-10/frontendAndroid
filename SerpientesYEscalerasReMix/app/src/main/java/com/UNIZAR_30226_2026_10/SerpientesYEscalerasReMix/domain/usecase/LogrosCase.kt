package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R

data class LogroUsuario(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val progresoActual: Int,
    val progresoObjetivo: Int,
    val tipoRecompensa: String,
    val valorRecompensa: String,
    val esCompletado: Boolean,
    val recompensaReclamada: Boolean = false,
    val imagen: Int = R.drawable.tablero_debug
)