package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model

import com.google.gson.annotations.SerializedName
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Producto

data class ProductoDto (
    @SerializedName("nombre") val nombre: String,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("precio") val precio: Int,
    @SerializedName("imagenUrl") val imagenUrl: String,
    @SerializedName ("categoria") val categoria: String
)

fun ProductoDto.toDomain(): Producto {
    return Producto(
        nombre = this.nombre,
        descripcion = this.descripcion,
        precio = this.precio,
        imagenUrl = this.imagenUrl,
        categoria = this.categoria
    )
}