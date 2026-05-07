package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model


import com.google.gson.annotations.SerializedName

data class SnapshotTablero(
    @SerializedName("casillas")
    val casillas: List<Casilla>
)