package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model

data class Lobby(
    val id: String,
    val hostUsername: String,
    val players: List<JugadorLobby?>,
    val tableroSelect: String
)