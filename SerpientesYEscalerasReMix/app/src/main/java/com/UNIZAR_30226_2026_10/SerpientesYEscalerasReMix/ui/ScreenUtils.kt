package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
    // TODO ajustar debidamente a los iconos de R (una vez sean creados, etc)
    val iconoR = when (icono) {
        "default" -> R.drawable.icono_default
        "bot"     -> R.drawable.icono_bots
        else -> R.drawable.icono_default
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
        else -> R.drawable.carta_moises
    }
}
