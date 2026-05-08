package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.fakes

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.EfectoActivo
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.FaseJuego
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.JugadorEstado
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.JugadoresSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fichas_amarillas
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fichas_azules
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fichas_rojas
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fichas_verdes

val fakeJugadoresSnapshot = JugadoresSnapshot(
    turno = 0,
    ronda = 10,
    jugadores = listOf(
        JugadorEstado(esLider = true, username = "YO", icono = "default", fase = FaseJuego.Cartas, mazo = "mazoEj", mano = listOf("Moises", "Moises", "Moises"), efectosActivos = emptyList<EfectoActivo>(), color = color_fichas_rojas),
        JugadorEstado(username = "luis", icono = "default", fase = FaseJuego.Cartas, mazo = "mazoEj", mano = listOf("Moises", "Moises", "Moises"), efectosActivos = emptyList<EfectoActivo>(), color = color_fichas_azules),
        JugadorEstado(username = "marta", icono = "default", fase = FaseJuego.Cartas, mazo = "mazoEj", mano = listOf("Moises", "Moises", "Moises"), efectosActivos = emptyList<EfectoActivo>(), color = color_fichas_verdes),
        JugadorEstado(username = "pablo", icono = "default", fase = FaseJuego.Cartas, mazo = "mazoEj", mano = listOf("Moises", "Moises", "Moises"), efectosActivos = emptyList<EfectoActivo>(), color = color_fichas_amarillas)
    )
)