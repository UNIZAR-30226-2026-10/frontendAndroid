package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model


import com.google.gson.annotations.SerializedName

data class ParidaReply(
    @SerializedName("barajas")
    val barajas: List<Baraja>,
    @SerializedName("configuracion")
    val configuracion: Configuracion,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("estado")
    val estado: String,
    @SerializedName("ganador")
    val ganador: Any,
    @SerializedName("ganadorEmail")
    val ganadorEmail: Any,
    @SerializedName("id")
    val id: String,
    @SerializedName("partidaJugadores")
    val partidaJugadores: List<PartidaJugadore>,
    @SerializedName("snapshotJugadores")
    val snapshotJugadores: SnapshotJugadores,
    @SerializedName("snapshotTablero")
    val snapshotTablero: SnapshotTablero,
    @SerializedName("tableroInicial")
    val tableroInicial: TableroInicial,
    @SerializedName("tableroInicialNombre")
    val tableroInicialNombre: String
)