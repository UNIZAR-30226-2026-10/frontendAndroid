package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model


import com.google.gson.annotations.SerializedName

data class SnapshotJugadores(
    @SerializedName("jugadores")
    val jugadoresPartidaReplies: List<JugadoresPartidaReply>,
    @SerializedName("ronda")
    val ronda: Int,
    @SerializedName("turnoActual")
    val turnoActual: Int
)