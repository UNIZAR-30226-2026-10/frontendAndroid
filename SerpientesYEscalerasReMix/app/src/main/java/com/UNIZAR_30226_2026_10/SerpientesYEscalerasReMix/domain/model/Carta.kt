package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model

enum class Tipo_Carta {
    Ofensiva,
    Defensiva,
    Entorno
}

enum class Calidad {
    Comun,
    Rara,
    Epica,
    Legendaria
}

data class Carta (
    val nombre: String,
    val descripcion: String,
    val tipo: Tipo_Carta,
    val calidad: Calidad,
    val id: Int? = null,
    val efecto: String? = null,
    val imagen: Int? = null
)
