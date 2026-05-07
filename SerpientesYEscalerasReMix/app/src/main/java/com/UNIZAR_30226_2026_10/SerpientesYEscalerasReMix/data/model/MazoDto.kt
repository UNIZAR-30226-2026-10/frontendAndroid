package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Mazo
import com.google.gson.annotations.SerializedName

data class MazoDto (
    @SerializedName("nombre") val nombre: String,
    @SerializedName("cartas") val cartas: List<CartaDto>
)

fun MazoDto.toDomain(): Mazo {
    return Mazo(
        nombre = this.nombre,
        cartas = this.cartas.map { it.toDomain() }
    )
}