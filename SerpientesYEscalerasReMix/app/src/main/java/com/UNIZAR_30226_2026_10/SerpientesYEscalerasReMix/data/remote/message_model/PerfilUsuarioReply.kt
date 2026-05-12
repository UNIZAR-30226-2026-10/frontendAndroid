package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model

import com.google.gson.annotations.SerializedName

data class PerfilUsuarioReply(
    @SerializedName("nombre")          val nombre: String,
    @SerializedName("victorias")       val victorias: Int,
    @SerializedName("derrotas")        val derrotas: Int,
    @SerializedName("SEP")             val monedas: Int,
    @SerializedName("iconoActual")     val iconoActual: String,
    @SerializedName("EscaleraActual")  val skinEscaleraActual: String,
    @SerializedName("SerpienteActual") val skinSerpienteActual: String,
    @SerializedName("FichaActual")     val skinFichaActual: String
)