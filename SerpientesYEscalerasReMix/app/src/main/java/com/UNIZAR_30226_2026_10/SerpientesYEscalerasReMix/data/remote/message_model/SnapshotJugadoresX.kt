package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model


import com.google.gson.annotations.SerializedName

data class SnapshotJugadoresX(
    @SerializedName("jugadores")
    val jugadores: List<Jugadore>,
    @SerializedName("ronda")
    val ronda: Int,
    @SerializedName("turnoActual")
    val turnoActual: Int
)