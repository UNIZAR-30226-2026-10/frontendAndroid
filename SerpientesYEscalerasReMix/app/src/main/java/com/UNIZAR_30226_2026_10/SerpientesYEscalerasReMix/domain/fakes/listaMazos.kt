package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.fakes

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Carta
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Calidad
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Tipo
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Mazo

val carta1: Carta = Carta(
    nombre = "Carta 1",
    descripcion = "Descripción de la carta 1",
    tipo = Tipo.Ofensiva,
    calidad = Calidad.Comun,
    id = 1,
    efecto = "Efecto de la carta 1",
    imagen = 0 //TEMP FIXME
)

val carta2: Carta = Carta(
    nombre = "Carta 2",
    descripcion = "Descripción de la carta 2",
    tipo = Tipo.Defensiva,
    calidad = Calidad.Rara,
    id = 2,
    efecto = "Efecto de la carta 2",
    imagen = 0 //TEMP FIXME
)

val carta3: Carta = Carta(
    nombre = "Carta 3",
    descripcion = "Descripción de la carta 3",
    tipo = Tipo.Entorno,
    calidad = Calidad.Epica,
    id = 3,
    efecto = "Efecto de la carta 3",
    imagen = 0 //TEMP FIXME
)

val carta4: Carta = Carta(
    nombre = "Carta 4",
    descripcion = "Descripción de la carta 4",
    tipo = Tipo.Ofensiva,
    calidad = Calidad.Legendaria,
    id = 4,
    efecto = "Efecto de la carta 4",
    imagen = 0 //TEMP FIXME
)

val carta5: Carta = Carta(
    nombre = "Carta 5",
    descripcion = "Descripción de la carta 5",
    tipo = Tipo.Defensiva,
    calidad = Calidad.Comun,
    id = 5,
    efecto = "Efecto de la carta 5",
    imagen = 0 //TEMP FIXME
)

val mazoVacio: Mazo = Mazo(
    nombre = "Mazo 1",
    cartas = emptyList(),
)

val mazo1: Mazo = Mazo(
    nombre = "Snake Slayer",
    cartas = listOf(
        carta1,
        carta1,
        carta2,
        carta2,
        carta3,
        carta3,
        carta4,
        carta4,
        carta5,
        carta5
    )
)

val mazo2: Mazo = Mazo(
    nombre = "Ladder Master",
    cartas = listOf(
        carta2,
        carta2,
        carta3,
        carta3,
        carta4,
        carta4,
        carta5,
        carta5,
        carta1,
        carta1
    )
)

val mazo3: Mazo = Mazo(
    nombre = "Balanced Deck",
    cartas = listOf(
        carta3,
        carta3,
        carta4,
        carta4,
        carta5,
        carta5,
        carta1,
        carta1,
        carta2,
        carta2
    )
)

val mazo4: Mazo = Mazo(
    nombre = "Offensive Power",
    cartas = listOf(
        carta4,
        carta4,
        carta5,
        carta5,
        carta1,
        carta1,
        carta2,
        carta2,
        carta3,
        carta3
    )
)

val mazo5: Mazo = Mazo(
    nombre = "Defensive Wall",
    cartas = listOf(
        carta5,
        carta5,
        carta1,
        carta1,
        carta2,
        carta2,
        carta3,
        carta3,
        carta4,
        carta4
    )
)

val mazo6: Mazo = Mazo(
    nombre = "Epic Collection",
    cartas = listOf(
        carta3,
        carta3,
        carta4,
        carta4,
        carta5,
        carta5,
        carta1,
        carta1,
        carta2,
        carta2
    )
)

val mazo7: Mazo = Mazo(
    nombre = "Legendary Set",
    cartas = listOf(
        carta4,
        carta4,
        carta5,
        carta5,
        carta1,
        carta1,
        carta2,
        carta2,
        carta3,
        carta3
    )
)

val mazo8: Mazo = Mazo(
    nombre = "Random Mix",
    cartas = listOf(
        carta1,
        carta2,
        carta3,
        carta4,
        carta5,
        carta1,
        carta2,
        carta3,
        carta4,
        carta5
    )
)

val listaDeMazosDePrueba = listOf(
    mazo1,
    mazo2,
    mazo3,
    mazo4,
    mazo5,
    mazo6,
    mazo7,
    mazo8
)