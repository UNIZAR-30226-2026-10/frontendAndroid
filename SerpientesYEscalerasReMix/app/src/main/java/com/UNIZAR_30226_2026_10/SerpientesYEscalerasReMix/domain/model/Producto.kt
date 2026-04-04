package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model

data class Producto(
    val nombre: String,
    val descripcion: String,
    val precio: Int,
    val imagenUrl: String,
    val categoria: String
)