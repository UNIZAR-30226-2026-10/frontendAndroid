package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model

enum class Tipo_Producto {
    Icono,
    Ficha,
    Serpiente,
    Escalera,
    Desconocido
}

data class Producto(
    val nombre: String,
    val precio: Int,
    val descripcion: String,
    val tipo: Tipo_Producto,
    val enPosesion: Boolean
)