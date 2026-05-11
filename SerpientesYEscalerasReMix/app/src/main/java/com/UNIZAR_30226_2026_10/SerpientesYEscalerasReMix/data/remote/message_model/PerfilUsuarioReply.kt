package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model

import com.google.gson.annotations.SerializedName

data class PerfilUsuarioReply(
    @SerializedName("nombre") val nombre: String,
    @SerializedName("victorias") val victorias: Int,
    @SerializedName("derrotas") val derrotas: Int,
    @SerializedName("monedas") val monedas: Int,
    @SerializedName("iconoActual") val iconoActual: String,
    @SerializedName("skinEscaleraActual") val skinEscaleraActual: String,
    @SerializedName("skinSerpienteActual") val skinSerpienteActual: String,
    @SerializedName("skinFichaActual") val skinFichaActual: String
)