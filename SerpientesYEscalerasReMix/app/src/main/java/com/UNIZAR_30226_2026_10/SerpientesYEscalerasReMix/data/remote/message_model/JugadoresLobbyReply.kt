package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model


import com.google.gson.annotations.SerializedName

data class JugadoresLobbyReply(
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("esIA")
    val esIA: Boolean,
    @SerializedName("estaListo")
    val estaListo: Boolean,
    @SerializedName("nombreMazo")
    val nombreMazo: String?,
    @SerializedName("icono")
    val icono: String?
)