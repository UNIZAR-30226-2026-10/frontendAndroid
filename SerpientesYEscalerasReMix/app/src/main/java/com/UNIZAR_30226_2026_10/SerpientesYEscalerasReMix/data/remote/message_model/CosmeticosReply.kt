package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model

import com.google.gson.annotations.SerializedName

// GET /api/users/{email}/icons
data class IconosReply(
    @SerializedName("iconos") val iconos: List<String>
)

// GET /api/users/{email}/pawns
data class FichasReply(
    @SerializedName("fichas") val fichas: List<String>
)

// GET /api/users/{email}/snakes
data class SerpientesReply(
    @SerializedName("serpientes") val serpientes: List<String>
)

// GET /api/users/{email}/stairs
data class EscalerasReply(
    @SerializedName("escaleras") val escaleras: List<String>
)

// Body para PUT /api/users/{email}/icon
data class ActualizarIconoRequest(
    @SerializedName("icon") val icon: String
)

// Body para PUT /api/users/{email}/pawn
data class ActualizarFichaRequest(
    @SerializedName("pawn") val token: String
)

// Body para PUT /api/users/{email}/snake
data class ActualizarSerpienteRequest(
    @SerializedName("snake") val snake: String
)

// Body para PUT /api/users/{email}/stair
data class ActualizarEscaleraRequest(
    @SerializedName("stair") val ladder: String
)