package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model


import com.google.gson.annotations.SerializedName

data class Casilla(
    @SerializedName("efecto")
    val efecto: String,
    @SerializedName("saltoA")
    val saltoA: Int?,
    @SerializedName("siguientes")
    val siguientes: List<Int>,
    @SerializedName("tipo")
    val tipo: String?
)