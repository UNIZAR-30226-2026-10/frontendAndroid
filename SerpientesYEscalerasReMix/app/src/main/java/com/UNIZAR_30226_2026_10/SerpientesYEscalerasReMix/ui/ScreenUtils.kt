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
fun buscarIconoR(icono: String): Int {
    // TODO ajustar debidamente a los iconos de R (una vez sean creados, etc)
    val iconoR = when (icono) {
        "default" -> R.drawable.icono_default
        "bot"     -> R.drawable.icono_bots
        else -> R.drawable.icono_default
    }

    return iconoR
}
