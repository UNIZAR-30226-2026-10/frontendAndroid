package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model

data class JugadorLobby(
    val email: String, // o id si es bot
    val username: String,
    val profileIcon: String = "default",
    val isReady: Boolean,
    val isBot: Boolean = false,
    val deckName: String? = null
)