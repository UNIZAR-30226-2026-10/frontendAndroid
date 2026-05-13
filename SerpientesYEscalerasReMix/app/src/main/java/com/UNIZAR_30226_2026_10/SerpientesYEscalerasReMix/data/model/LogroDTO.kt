package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model

import com.google.gson.annotations.SerializedName

// Coincide con la respuesta de GET /api/achievements/
data class LogroDTO(
    @SerializedName("nombre")              val id: String,
    @SerializedName("descripcion")         val descripcion: String,
    @SerializedName("requisito")           val objetivo: Int,
    @SerializedName("tipo")                val tipoRecompensa: String,
    @SerializedName("recompensaMonetaria") val valorRecompensa: Int?,
    @SerializedName("cartaID")             val cartaID: String?,
) {
    val nombre: String get() = id
}

// Coincide con la respuesta de GET /api/users/{email}/stats
data class StatsDTO(
    @SerializedName("victorias")         val victorias: Int = 0,
    @SerializedName("derrotas")          val derrotas: Int = 0,
    @SerializedName("SEP")               val sep: Int = 0,
    @SerializedName("CartasJugadas")     val cartasJugadas: Int = 0,
    @SerializedName("PartidasJugadas")   val partidasJugadas: Int = 0,
    @SerializedName("NumeroAmigos")      val numeroAmigos: Int = 0,
    @SerializedName("CartasLegendarias") val cartasLegendarias: Int = 0,
    @SerializedName("LogrosCompletados") val logrosCompletados: List<String> = emptyList()
)

// Coincide con la respuesta de GET /api/users/{email}/achievements (logros ya reclamados)
data class LogrosReclamadosDTO(
    @SerializedName("achievements") val ids: List<String>
)

data class LogrosReply(
    @SerializedName("logros") val achievements: List<LogroDTO>
)