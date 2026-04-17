package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.fakes

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.EfectoActivo
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.FaseJuego
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.JugadorEstado
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.JugadoresSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fichas_amarillas
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fichas_azules
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fichas_rojas
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fichas_verdes

val fakeJugadoresSnapshot = JugadoresSnapshot(
    turnoActual = 10,
    ronda = 3,
    jugadores = listOf(
        JugadorEstado(esLider = true, email = "ana@gmail.com", nombre = "ana", icono = R.drawable.icono_default, fase = FaseJuego.Cartas, mazo = "mazoEj", mano = listOf("Moises", "Moises", "Moises"), efectosActivos = emptyList<EfectoActivo>(), color = color_fichas_rojas),
        JugadorEstado(email = null, nombre = "luis", icono = R.drawable.icono_default, fase = FaseJuego.Cartas, mazo = "mazoEj", mano = listOf("Moises", "Moises", "Moises"), efectosActivos = emptyList<EfectoActivo>(), color = color_fichas_azules),
        JugadorEstado(email = null, nombre = "marta", icono = R.drawable.icono_default, fase = FaseJuego.Cartas, mazo = "mazoEj", mano = listOf("Moises", "Moises", "Moises"), efectosActivos = emptyList<EfectoActivo>(), color = color_fichas_verdes),
        JugadorEstado(email = null, nombre = "pablo", icono = R.drawable.icono_default, fase = FaseJuego.Cartas, mazo = "mazoEj", mano = listOf("Moises", "Moises", "Moises"), efectosActivos = emptyList<EfectoActivo>(), color = color_fichas_amarillas)
    )
)