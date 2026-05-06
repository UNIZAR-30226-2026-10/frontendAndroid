package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model


import com.google.gson.annotations.SerializedName

data class InvitacionGetReply(
    @SerializedName("inviteFor")
    val inviteFor: String,
    @SerializedName("inviteFrom")
    val inviteFrom: String,
    @SerializedName("partidaID")
    val partidaID: String
)