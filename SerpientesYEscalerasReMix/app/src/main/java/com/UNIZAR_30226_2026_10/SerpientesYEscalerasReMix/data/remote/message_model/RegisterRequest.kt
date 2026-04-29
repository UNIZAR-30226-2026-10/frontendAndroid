package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model

data class RegisterRequest(
    val email: String,
    val username: String,
    val password: String
)