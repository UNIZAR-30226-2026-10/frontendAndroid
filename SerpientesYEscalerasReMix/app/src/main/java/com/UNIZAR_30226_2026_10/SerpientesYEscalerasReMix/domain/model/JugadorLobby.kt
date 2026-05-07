package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model

data class JugadorLobby(
    val username: String,
    val profileIcon: String = "default",
    val isReady: Boolean,
    val isBot: Boolean = false,
    val deckName: String? = null
)