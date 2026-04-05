package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model

enum class Tipo {
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
    val id: Int
    val nombre: String,
    val descripcion: String,
    val efecto, //TEMP FIXME
    val imagen: int, //TEMP FIXME
    val tipo: Tipo,
    val calidad: Calidad
)
