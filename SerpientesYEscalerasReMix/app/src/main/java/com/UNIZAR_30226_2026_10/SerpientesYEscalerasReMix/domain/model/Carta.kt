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
    val calidad: Calidad,
    val tipo: Tipo_Carta,
    val descripcion: String,
    val imagen: Int? = null
)
