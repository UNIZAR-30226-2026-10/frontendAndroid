package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote

// Coincide con la respuesta de /api/achievements/
data class LogroDTO(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val objetivo: Int,
    val tipoRecompensa: String,
    val valorRecompensa: String,
    val claveMetrica: String // Ej: "partidas_ganadas"
)

// Coincide con la respuesta de /api/users/{email}/stats
data class StatsDTO(
    val stats: Map<String, Int> // Diccionario con las métricas del usuario
)