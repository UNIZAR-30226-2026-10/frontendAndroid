package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model

import com.google.gson.annotations.SerializedName

// Coincide con la respuesta de GET /api/achievements/
data class LogroDTO(
    @SerializedName("id")              val id: String,
    @SerializedName("nombre")          val nombre: String,
    @SerializedName("descripcion")     val descripcion: String,
    @SerializedName("objetivo")        val objetivo: Int,
    @SerializedName("tipoRecompensa")  val tipoRecompensa: String,
    @SerializedName("valorRecompensa") val valorRecompensa: String,
    @SerializedName("claveMetrica")    val claveMetrica: String,
    // imagen es un recurso local drawable — se resuelve en el mapper, no viene de la API
    val imagen: Int = 0
)

// Coincide con la respuesta de GET /api/users/{email}/stats
data class StatsDTO(
    @SerializedName("stats") val stats: Map<String, Int>
)

// Coincide con la respuesta de GET /api/users/{email}/achievements (logros ya reclamados)
data class LogrosReclamadosDTO(
    @SerializedName("achievements") val ids: List<String>
)