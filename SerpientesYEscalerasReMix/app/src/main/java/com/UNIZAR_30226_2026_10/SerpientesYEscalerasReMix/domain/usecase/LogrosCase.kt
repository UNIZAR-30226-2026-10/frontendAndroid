package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase


data class LogroUsuario(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val progresoActual: Int,
    val progresoObjetivo: Int,
    val tipoRecompensa: String,
    val valorRecompensa: String,
    val imagen: Int, 
    val esCompletado: Boolean,
    val recompensaReclamada: Boolean
)