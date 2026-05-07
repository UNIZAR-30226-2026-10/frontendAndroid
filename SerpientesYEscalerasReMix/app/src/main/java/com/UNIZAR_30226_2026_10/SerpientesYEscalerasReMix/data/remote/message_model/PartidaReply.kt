package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model


import com.google.gson.annotations.SerializedName

data class PartidaReply(
    @SerializedName("chat")
    val chat: List<Chat>,
    @SerializedName("configuracion")
    val configuracion: ConfiguracionX,
    @SerializedName("estado")
    val estado: String,
    @SerializedName("ganador")
    val ganador: Any,
    @SerializedName("ID")
    val iD: String,
    @SerializedName("partidaJugadores")
    val partidaJugadores: List<PartidaJugadore>,
    @SerializedName("snapshotJugadores")
    val snapshotJugadores: SnapshotJugadoresX,
    @SerializedName("snapshotTablero")
    val snapshotTablero: SnapshotTableroX,
    @SerializedName("tableroInicialNombre")
    val tableroInicialNombre: String
)