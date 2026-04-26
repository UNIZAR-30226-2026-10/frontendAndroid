package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.fakes

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.FichaSnapshot

val fakeFichasSnapshot = listOf(
    FichaSnapshot(idJugador = "ana@gmail.com", id = 1, casilla = 1, idImg = R.drawable.jugador_rojo_explorador, esUsuario = true),
    FichaSnapshot(idJugador = "ana@gmail.com", id = 2, casilla = 1, idImg = R.drawable.jugador_rojo_explorador, esUsuario = true),
    FichaSnapshot(idJugador = "ana@gmail.com", id = 3, casilla = 7, idImg = R.drawable.jugador_rojo_explorador, esUsuario = true),

    FichaSnapshot(idJugador = "luis@gmail.com", id = 1, casilla = 1, idImg = R.drawable.jugador_azul_explorador, esUsuario = false),
    FichaSnapshot(idJugador = "luis@gmail.com", id = 2, casilla = 3, idImg = R.drawable.jugador_azul_explorador, esUsuario = false),
    FichaSnapshot(idJugador = "luis@gmail.com", id = 3, casilla = 3, idImg = R.drawable.jugador_azul_explorador, esUsuario = false),

    FichaSnapshot(idJugador = "marta@gmail.com", id = 1, casilla = 1, idImg = R.drawable.jugador_verde_explorador, esUsuario = false),
    FichaSnapshot(idJugador = "marta@gmail.com", id = 2, casilla = 1, idImg = R.drawable.jugador_verde_explorador, esUsuario = false),
    FichaSnapshot(idJugador = "marta@gmail.com", id = 3, casilla = 1, idImg = R.drawable.jugador_verde_explorador, esUsuario = false),

    FichaSnapshot(idJugador = "diego@gmail.com", id = 1, casilla = 1, idImg = R.drawable.jugador_amarillo_explorador, esUsuario = false),
    FichaSnapshot(idJugador = "diego@gmail.com", id = 2, casilla = 1, idImg = R.drawable.jugador_amarillo_explorador, esUsuario = false),
    FichaSnapshot(idJugador = "diego@gmail.com", id = 3, casilla = 1, idImg = R.drawable.jugador_amarillo_explorador, esUsuario = false)

)
