package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model

import com.google.gson.annotations.SerializedName

data class JugarCartaRequest(
    @SerializedName("card_id")
    val cardId: String,
    @SerializedName("who")
    val who: Any? = null,
    @SerializedName("inicio")
    val inicio: Int? = null,
    @SerializedName("fin")
    val fin: Int? = null
)