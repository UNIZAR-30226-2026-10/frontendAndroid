package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model

enum class TipoCasilla {
    Normal, Escalera, Serpiente, Bifurcacion, Meta, Vacio
}

data class CasillaSnapshot(
    val esCurva: Boolean,
    val rotacion: Int,
    val efecto: String? = null,
    val tipo: TipoCasilla,
    val siguientes: List<Int>,
    val saltoA: Int? = null,
    val hayBloqueo: Boolean = false
)

data class TableroSnapshot(
    val casillas: List<CasillaSnapshot>
)