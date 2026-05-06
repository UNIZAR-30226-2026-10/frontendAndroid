package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model

data class Usuario(
    val nombre: String,
    val estadoTexto: String = "", // "te ha invitado", etc.
    val haInvitado: Boolean = false,
    val lobbyInvitado: String = ""
)
