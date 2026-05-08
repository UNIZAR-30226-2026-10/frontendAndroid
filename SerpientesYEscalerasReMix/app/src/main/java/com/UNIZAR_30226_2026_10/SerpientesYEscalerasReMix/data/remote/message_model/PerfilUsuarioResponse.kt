package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model

import com.google.gson.annotations.SerializedName

data class PerfilUsuarioResponse(
    @SerializedName("username") val nombre: String,
    @SerializedName("wins") val victorias: Int,
    @SerializedName("losses") val derrotas: Int,
    @SerializedName("coins") val monedas: Int,
    @SerializedName("current_icon") val iconoActual: String,
    @SerializedName("current_ladder_skin") val skinEscaleraActual: String,
    @SerializedName("current_snake_skin") val skinSerpienteActual: String,
    @SerializedName("current_token_skin") val skinFichaActual: String
)