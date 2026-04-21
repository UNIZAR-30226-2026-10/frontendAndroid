package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.fakes

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Calidad
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Carta
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Tipo

val fakeManoCartas = MutableList<Carta?>(4) { idx -> // Usa llaves, no paréntesis
    when(idx) {
        0 -> Carta("Moises", "Te saltas el bloqueo", tipo = Tipo.Defensiva, calidad = Calidad.Epica)
        1 -> Carta("Moises", "Te saltas el bloqueo", tipo = Tipo.Defensiva, calidad = Calidad.Rara)
        2 -> Carta("Moises", "Te saltas el bloqueo", tipo = Tipo.Defensiva, calidad = Calidad.Comun)
        else -> null
    }
}