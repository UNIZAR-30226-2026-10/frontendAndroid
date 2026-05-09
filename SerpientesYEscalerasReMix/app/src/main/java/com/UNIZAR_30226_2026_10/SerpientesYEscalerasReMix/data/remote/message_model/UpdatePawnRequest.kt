package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model


import com.google.gson.annotations.SerializedName

data class UpdatePawnRequest(
    @SerializedName("final_position")
    val finalPosition: Int,
    @SerializedName("pawn_id")
    val pawnId: Int,
    @SerializedName("steps_remaining")
    val stepsRemaining: Int?
)