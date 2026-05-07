package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model


import com.google.gson.annotations.SerializedName

data class Configuracion(
    @SerializedName("numeroBots")
    val numeroBots: Int,
    @SerializedName("numeroJugadores")
    val numeroJugadores: Int,
    @SerializedName("tablero")
    val tablero: String
)