package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)