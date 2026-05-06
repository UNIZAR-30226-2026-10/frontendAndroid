package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import kotlinx.coroutines.flow.StateFlow

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
    val imagen: Int = 0
)

class ObtenerLogrosCase(
    private val email: StateFlow<String>
) {
    suspend operator fun invoke(): List<LogroUsuario> {
        // TODO: GET /api/achievements  → lista global de logros
        // TODO: GET /api/users/${email.value}/stats  → logros completados del usuario
        // Cruzar ambas respuestas: para cada logro global, comprobar si está
        // en la lista de completados del usuario y asignar progreso/esCompletado
        return emptyList()
    }
}