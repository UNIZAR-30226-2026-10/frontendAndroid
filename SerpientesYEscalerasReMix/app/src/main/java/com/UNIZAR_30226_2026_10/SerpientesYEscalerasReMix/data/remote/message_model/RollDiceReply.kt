package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model


import com.google.gson.annotations.SerializedName

data class RollDiceReply(
    @SerializedName("partida")
    val partida: PartidaReply,
    @SerializedName("movimientos")
    val movimientos: List<MovimientoRollDiceReply>,
    @SerializedName("tirada")
    val tirada: String,
    @SerializedName("tiradaExtra")
    val tiradaExtra: String
)