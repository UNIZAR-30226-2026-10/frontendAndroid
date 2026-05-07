package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model


import com.google.gson.annotations.SerializedName

data class JugadoresPartidaReply(
    @SerializedName("cartaJugadaEnTurno")
    val cartaJugadaEnTurno: Boolean,
    @SerializedName("cartasJugadas")
    val cartasJugadas: Int,
    @SerializedName("cementerio")
    val cementerio: List<Any>,
    @SerializedName("efectosActivos")
    val efectosActivos: List<Any>,
    @SerializedName("email")
    val email: String,
    @SerializedName("fase")
    val fase: String,
    @SerializedName("fichas")
    val fichas: List<Ficha>,
    @SerializedName("mano")
    val mano: List<String>,
    @SerializedName("mazo")
    val mazo: String,
    @SerializedName("mazoRestante")
    val mazoRestante: List<String>,
    @SerializedName("movimientosPermitidos")
    val movimientosPermitidos: List<Any>
)