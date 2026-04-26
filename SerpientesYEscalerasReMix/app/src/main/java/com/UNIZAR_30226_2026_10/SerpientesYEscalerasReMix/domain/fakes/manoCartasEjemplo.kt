package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.fakes

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Calidad
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Carta
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Tipo

val fakeManoCartas = MutableList<Carta?>(4) { idx -> // Usa llaves, no paréntesis
    when(idx) {
        0 -> Carta("Wild Frank", "Poner una serpiente", tipo = Tipo.Entorno, calidad = Calidad.Epica)
        1 -> Carta("Pickpocket", "Roba una carta al azar de otro jugador", tipo = Tipo.Ofensiva, calidad = Calidad.Rara)
        2 -> Carta("Robo de identidad", "Cambia una de tus fihcas por otra al azar", tipo = Tipo.Defensiva, calidad = Calidad.Comun)
        else -> null
    }
}