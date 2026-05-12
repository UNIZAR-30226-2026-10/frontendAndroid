package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model


import com.google.gson.annotations.SerializedName

data class PartidaRegistroGetReply(
    @SerializedName("fecha")
    val fecha: String,
    @SerializedName("ID")
    val iD: String,
    @SerializedName("jugadores")
    val jugadores: List<String>,
    @SerializedName("mapa")
    val mapa: String
)