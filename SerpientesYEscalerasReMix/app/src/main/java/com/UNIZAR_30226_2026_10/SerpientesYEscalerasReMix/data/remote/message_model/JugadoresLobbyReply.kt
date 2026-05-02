package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model


import com.google.gson.annotations.SerializedName

data class JugadoresLobbyReply(
    @SerializedName("esIA")
    val esIA: Boolean,
    @SerializedName("estaListo")
    val estaListo: Boolean,
    @SerializedName("icono")
    val icono: String,
    @SerializedName("idJugador")
    val idJugador: String,
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("nombreMazo")
    val nombreMazo: String
)