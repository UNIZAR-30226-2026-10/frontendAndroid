package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model

import com.google.gson.annotations.SerializedName

data class SaldoDto (
    @SerializedName("sep") val saldo: Int,
)

fun SaldoDto.toDomain(): Int {
    return this.saldo
}