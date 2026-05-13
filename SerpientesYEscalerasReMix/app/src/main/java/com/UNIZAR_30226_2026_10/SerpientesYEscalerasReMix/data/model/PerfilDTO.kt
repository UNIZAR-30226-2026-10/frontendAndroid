package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model

data class PerfilUsuario(
    val nombre: String?,
    val victorias: Int,
    val derrotas: Int,
    val monedas: Int,
    val iconoActual: String?,
    val skinEscaleraActual: String?,
    val skinSerpienteActual: String?,
    val skinFichaActual: String?
)

enum class CategoriaCosmetico { ESCALERA, SERPIENTE, FICHA, ICONO }