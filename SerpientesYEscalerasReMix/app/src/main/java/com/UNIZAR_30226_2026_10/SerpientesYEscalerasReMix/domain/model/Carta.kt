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
    val id: Int,
    val nombre: String = "Carta $id",
    val descripcion: String = "Descripción de la carta $id",
    val efecto: String = "Efecto $id", //TEMP FIXME
    val imagen: Int, //TEMP FIXME
    val tipo: Tipo = Tipo.Ofensiva, //TEMP FIXME
    val calidad: Calidad = Calidad.Comun //TEMP FIXME
)
