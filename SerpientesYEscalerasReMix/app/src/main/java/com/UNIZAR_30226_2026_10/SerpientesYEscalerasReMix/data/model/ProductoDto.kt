package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model

import com.google.gson.annotations.SerializedName
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Producto
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Tipo_Producto

data class ProductoDto (
    @SerializedName("nomCosmetico") val nombre: String?,
    @SerializedName("precio") val precio: Int?,
    @SerializedName("desc") val descripcion: String?,
    @SerializedName("tipo") val tipo: String?,
    @SerializedName("loTiene") val enPosesion: Boolean?
)

fun ProductoDto.toDomain(): Producto {
    val normalizedName = (this.nombre ?: "")
        .lowercase()
        .replace(" ", "_")
        .replace("-", "_")
    val imageResId = PRODUCTO_IMAGE_MAP[normalizedName]

    // Pasar de serpiente_calcetin a "Serpiente Calcetin"
    val nombreAMostrar = (this.nombre ?: "")
        .split("_", " ")
        .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }


    return Producto(
        nombreId = this.nombre ?: "",
        nombreAMostrar = nombreAMostrar,
        precio = this.precio ?: 0,
        descripcion = this.descripcion ?: "",
        // Convertir el tipo de String a Tipo enum, manejando casos desconocidos
        tipo = when (this.tipo) {
            "Icono" -> Tipo_Producto.Icono
            "Skin_Ficha" -> Tipo_Producto.Ficha
            "Skin_Serpiente" -> Tipo_Producto.Serpiente
            "Skin_Escalera" -> Tipo_Producto.Escalera
            else -> Tipo_Producto.Desconocido
        },
        enPosesion = this.enPosesion ?: false,
        imageResId = imageResId

    )
}

private val PRODUCTO_IMAGE_MAP = mapOf(
    "icono_nerd" to R.drawable.icono_nerd,
    "icono_cofre" to R.drawable.icono_cofre,
    "ficha_totem" to R.drawable.ficha_totem,
    "ficha_aventurero" to R.drawable.ficha_aventurero,
    "ficha_esqueleto" to R.drawable.ficha_esqueleto,
    "ficha_moneda" to R.drawable.ficha_moneda,
    "serpiente_calcetin" to R.drawable.serpiente_calcetin,
    "serpiente_tribal" to R.drawable.serpiente_tribal,
    "serpiente_futuro" to R.drawable.serpiente_futuro,
    "escalera_jungla" to R.drawable.escalera_jungla
)

data class ComprarProductoRequest(
    @SerializedName("cosmetic_name") val nombreCosmetico: String
)
