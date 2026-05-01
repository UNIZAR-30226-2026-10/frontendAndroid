package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model

data class Lobby(
    val id: String,
    val hostEmail: String,
    val players: List<JugadorLobby?>
)