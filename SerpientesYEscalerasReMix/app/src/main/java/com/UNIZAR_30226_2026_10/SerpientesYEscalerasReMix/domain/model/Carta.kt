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
    val imagen: Int? = null,
    //FIXME TEMP PARA QUE NO DE ERRORES; SI FUNCIONA BORRA TODAS LAS REFERENCIAS A ESTOS ATRIB
    val id: Int? = null,
    val efecto: String? = null
)
