package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model


import com.google.gson.annotations.SerializedName

data class Baraja(
    @SerializedName("barajaNombre")
    val barajaNombre: String,
    @SerializedName("barajaUsuarioEmail")
    val barajaUsuarioEmail: String,
    @SerializedName("partidaId")
    val partidaId: String
)