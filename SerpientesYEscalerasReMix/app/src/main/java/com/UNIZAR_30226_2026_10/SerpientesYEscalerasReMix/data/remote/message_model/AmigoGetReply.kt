package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model


import com.google.gson.annotations.SerializedName

data class AmigoGetReply(
    @SerializedName("icono")
    val icono: String,
    @SerializedName("nombre")
    val nombre: String
)