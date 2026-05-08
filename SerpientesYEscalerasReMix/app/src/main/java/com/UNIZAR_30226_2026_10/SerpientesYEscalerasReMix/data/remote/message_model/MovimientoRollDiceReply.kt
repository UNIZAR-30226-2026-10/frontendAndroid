package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model


import com.google.gson.annotations.SerializedName

data class MovimientoRollDiceReply(
    @SerializedName("casillaDestino")
    val casillaDestino: String,
    @SerializedName("esBifurcacion")
    val esBifurcacion: String,
    @SerializedName("fichaId")
    val fichaId: String,
    @SerializedName("pasosRestantes")
    val pasosRestantes: String?
)