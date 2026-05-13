package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model

import com.google.gson.annotations.SerializedName

data class MazosResponseDto(
    @SerializedName("decks") val decks: List<MazoDto>
)
