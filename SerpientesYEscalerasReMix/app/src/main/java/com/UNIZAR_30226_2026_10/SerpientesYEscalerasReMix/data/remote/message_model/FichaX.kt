package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model


import com.google.gson.annotations.SerializedName

data class FichaX(
    @SerializedName("casilla")
    val casilla: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("meta")
    val meta: Boolean
)