package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model


import com.google.gson.annotations.SerializedName

data class SnapshotTableroX(
    @SerializedName("casillas")
    val casillas: List<String>,
    @SerializedName("escaleras")
    val escaleras: List<String>,
    @SerializedName("serpientes")
    val serpientes: List<String>
)