package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model


import com.google.gson.annotations.SerializedName

data class PartidaJugadore(
    @SerializedName("escaleraActualField")
    val escaleraActualField: String,
    @SerializedName("fichaActualField")
    val fichaActualField: String,
    @SerializedName("iconoActualField")
    val iconoActualField: String,
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("serpienteActualField")
    val serpienteActualField: String
)