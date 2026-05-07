package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model


import com.google.gson.annotations.SerializedName

data class LobbyReply(
    @SerializedName("idCreador")
    val idCreador: String,
    @SerializedName("idLobby")
    val idLobby: String,
    @SerializedName("jugadores")
    val jugadores: List<JugadoresLobbyReply>,
    @SerializedName("numBots")
    val numBots: Int,
    @SerializedName("numJugadores")
    val numJugadores: Int,
    @SerializedName("tablero")
    val tablero: String,
    @SerializedName("idPartida")
    val idPartida: String?
)