package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model

data class MsgChat(
    val sender: String,
    val message: String,
    val isSystem: Boolean = false
)
