package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.CartaDto
import com.google.gson.annotations.SerializedName
import retrofit2.http.Body

data class EditarMazoRequest (
    @SerializedName("nombre") val nuevoNombre: String?,
    @SerializedName("cartas") val cartas: List<CartaDto>?
)