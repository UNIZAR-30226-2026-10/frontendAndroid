package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model

data class Usuario(
    val nombre: String,
    val estadoTexto: String, // "online", "te ha invitado", etc.
    val estaOnline: Boolean,
    val esAmigo: Boolean = false,
    val haInvitado: Boolean = false
)
