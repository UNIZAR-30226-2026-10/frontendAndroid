package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model


import com.google.gson.annotations.SerializedName

data class Jugadore(
    @SerializedName("cartaJugadaEnTurno")
    val cartaJugadaEnTurno: Boolean,
    @SerializedName("cartasJugadas")
    val cartasJugadas: Int,
    @SerializedName("cementerio")
    val cementerio: List<Any?>,
    @SerializedName("efectosActivos")
    val efectosActivos: List<Any?>,
    @SerializedName("fase")
    val fase: String,
    @SerializedName("fichas")
    val fichas: List<FichaX>,
    @SerializedName("mano")
    val mano: List<String>,
    @SerializedName("mazo")
    val mazo: String,
    @SerializedName("mazoRestante")
    val mazoRestante: List<String>,
    @SerializedName("movimientosPermitidos")
    val movimientosPermitidos: List<Any?>,
    @SerializedName("username")
    val username: String
)