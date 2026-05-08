package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController

@Composable
fun fijarOrientacion(orientation: Int) {
    val context = LocalContext.current

    DisposableEffect(orientation) {
        val activity = context as? Activity
        val originalOrientation = activity?.requestedOrientation

        // Solo cambiamos si la orientación actual es distinta a la que pedimos
        if (activity?.requestedOrientation != orientation) {
            activity?.requestedOrientation = orientation
        }

        onDispose { }
    }
}

// Accede a la actividad para indicarle que se gire (si tiene que hacerlo) antes de que se destruya
// la vista cargada en la actividad
fun prepararOrientacion(SEState: SENavHostController, Orientation: Int) {
    val activity = SEState.navController.context as? Activity
    activity?.requestedOrientation = Orientation
}

// Busca Icono de jugador en R
fun buscarIconoJugadorR(icono: String): Int {
    val iconoR = when (icono) {
        "icono_default" -> R.drawable.icono_jugador_default
        "icono_nerd" -> R.drawable.icono_jugador_nerd
        "icono_completista" -> R.drawable.icono_jugador_completista
        "icono_platino" -> R.drawable.icono_jugador_platino
        "icono_L" -> R.drawable.icono_jugador_l
        "icono_W" -> R.drawable.icono_jugador_w
        "icono_cofre" -> R.drawable.icono_jugador_cofre
        "bot" -> R.drawable.icono_bots
        else -> R.drawable.icono_jugador_default
    }

    return iconoR
}

// Busca Icono de carta en R
fun buscarIconoCartaR(icono: String): Int {
    return when (icono) {
        "Moises" -> R.drawable.carta_moises
        "Wild Frank" -> R.drawable.carta_wild_frank
        "Carpintero" -> R.drawable.carta_carpintero
        "Día de la marmota" -> R.drawable.carta_dia_de_la_marmota
        "Salto de longitud" -> R.drawable.carta_salto_de_longitud
        "Robo de identidad" -> R.drawable.carta_robo_de_identidad
        "Mal de ojo" -> R.drawable.carta_mal_de_ojo
        "Antidoto" -> R.drawable.carta_antidoto
        "Pickpocket" -> R.drawable.carta_pickpocket
        "Dado envenenado" -> R.drawable.carta_dado_envenenado
        "Dado dorado" -> R.drawable.carta_dado_dorado
        "Serpiente en tu bota" -> R.drawable.cata_serpiente_en_tu_bota
        "Parca" -> R.drawable.carta_parca
        "Cambiar de idea" -> R.drawable.carta_cambiar_de_idea
        "Agujero de serpiente" -> R.drawable.carta_agujero_de_serpiente
        "Bolsillo roto" -> R.drawable.carta_bolsillo_roto
        "Compañerismo obligado" -> R.drawable.carta_companerismo_obligatorio
        "Coleccionista" -> R.drawable.carta_coleccionista
        "Noqueo" -> R.drawable.carta_noqueo
        "Exceso de medios" -> R.drawable.carta_exceso_de_medios
        else -> R.drawable.carta_moises
    }
}

// Busca Icono de efecto en R
fun buscarIconoEfectoR(efecto: String): Int {
    return when (efecto) {
        "+4" -> R.drawable.efecto_mas_cuatro
        "-4" -> R.drawable.efecto_menos_cuatro
        "Agujero de serpiente" -> R.drawable.efecto_agujero_de_serpiente
        else -> R.drawable.debug_error
    }
}

// Busca Icono de ficha en R
fun buscarIconoFichaR(ficha: String, color: Color): Int {
    return when (ficha) {
        "ficha_aventurero" -> if (color == Color.Red) R.drawable.jugador_rojo_explorador
        else if (color == Color.Blue) R.drawable.jugador_azul_explorador
        else if (color == Color.Green) R.drawable.jugador_verde_explorador
        else R.drawable.jugador_amarillo_explorador

        "ficha_esqueleto" -> if (color == Color.Red) R.drawable.jugador_rojo_calavera
        else if (color == Color.Blue) R.drawable.jugador_azul_calavera
        else if (color == Color.Green) R.drawable.jugador_verde_calavera
        else R.drawable.jugador_amarillo_calavera

        "ficha_totem" -> if (color == Color.Red) R.drawable.jugador_rojo_totem
        else if (color == Color.Blue) R.drawable.jugador_azul_totem
        else if (color == Color.Green) R.drawable.jugador_verde_totem
        else R.drawable.jugador_amarillo_totem

        else -> if (color == Color.Red) R.drawable.jugador_rojo_explorador
        else if (color == Color.Blue) R.drawable.jugador_azul_explorador
        else if (color == Color.Green) R.drawable.jugador_verde_explorador
        else R.drawable.jugador_amarillo_explorador
    }
}

// Busca Cabeza de serpiente en R
fun buscarCabezaSerpienteR(serpiente: String): Int {
    return when (serpiente) {
        "serpiente_calcetin" -> R.drawable.serpiente_calcetin_cabeza
        "serpiente_tribal" -> R.drawable.serpiente_tribal_cabeza
        "serpiente_futuro" -> R.drawable.serpiente_futuro_cabeza
        else -> R.drawable.serpiente_base_cabeza
    }
}

// Busca Cuerpo de serpiente en R
fun buscarCuerpoSerpienteR(serpiente: String): Int {
    return when (serpiente) {
        "serpiente_calcetin" -> R.drawable.serpiente_calcetin_cuerpo
        "serpiente_tribal" -> R.drawable.serpiente_tribal_cuerpo
        "serpiente_futuro" -> R.drawable.serpiente_futuro_cuerpo
        else -> R.drawable.serpiente_base_cuerpo
    }
}

// Busca Cola de serpiente en R
fun buscarColaSerpienteR(serpiente: String): Int {
    return when (serpiente) {
        "serpiente_calcetin" -> R.drawable.serpiente_calcetin_cola
        "serpiente_tribal" -> R.drawable.serpiente_tribal_cola
        "serpiente_futuro" -> R.drawable.serpiente_futuro_cola
        else -> R.drawable.serpiente_base_cola
    }
}

// Busca Escalera en R
fun buscarEscaleraR(escalera: String): Int {
    return when (escalera) {
        "escalera_estratega" -> R.drawable.escalera_estratega
        "escalera_magnate" -> R.drawable.escalera_magnate
        "escalera_jungla" -> R.drawable.escalera_jungla
        else -> R.drawable.escalera
    }
}

// Busca la miniatura del tablero
fun buscarMiniaturaTableroR(tablero: String): Int {
    return when (tablero) {
        "Basico" -> R.drawable.miniatura_mapa_basico
        "Jungla Loca" -> R.drawable.miniatura_mapa_jungla_loca
        "La apuesta final" -> R.drawable.miniatura_mapa_la_apuesta_final
        else -> R.drawable.miniatura_mapa_basico
    }
}

