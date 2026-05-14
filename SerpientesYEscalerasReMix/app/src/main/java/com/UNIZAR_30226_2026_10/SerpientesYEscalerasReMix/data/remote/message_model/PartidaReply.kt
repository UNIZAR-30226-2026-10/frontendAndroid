package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model


import com.google.gson.annotations.SerializedName

data class PartidaReply(
    @SerializedName("ID")
    val iD: String,
    @SerializedName("estado")
    val estado: String,
    @SerializedName("fechaInicio")
    val fechaInicio: String,
    @SerializedName("fechaFin")
    val fechaFin: String?,
    @SerializedName("configuracion")
    val configuracion: ConfiguracionX,
    @SerializedName("tableroInicialNombre")
    val tableroInicialNombre: String,
    @SerializedName("ganador")
    val ganador: Ganador?,
    @SerializedName("partidaJugadores")
    val partidaJugadores: List<PartidaJugadore>,
    @SerializedName("chat")
    val chat: List<ChatMsg>,
    @SerializedName("snapshotTablero")
    val snapshotTablero: SnapshotTablero,
    @SerializedName("snapshotJugadores")
    val snapshotJugadores: SnapshotJugadoresX
)