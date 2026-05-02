package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import kotlinx.coroutines.flow.StateFlow

class ObtenerPerfilCase(
    private val email: StateFlow<String>,
    private val username: StateFlow<String>
) {
    suspend operator fun invoke(): PerfilUsuario {
        val nombreActual = username.value
        return PerfilUsuario(
            nombre              = if (nombreActual.isBlank()) "SerpienteGanadora5" else nombreActual,
            victorias           = 35,
            derrotas            = 12,
            monedas             = 500,
            iconoActual         = "icono_default",
            skinEscaleraActual  = "escalera_default",
            skinSerpienteActual = "serpiente_default",
            skinFichaActual     = "ficha_default"
        )
    }
}