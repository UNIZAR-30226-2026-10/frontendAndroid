package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model

import com.google.gson.annotations.SerializedName

data class CartasDisponiblesResponseDto(
    @SerializedName("cards") val cartas: List<CartaDto>
)
