package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController

@Composable
fun LockScreenOrientation(orientation: Int) {
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
fun prepareScreenOrientation(SEState: SENavHostController, Orientation: Int) {
    val activity = SEState.navController.context as? Activity
    activity?.requestedOrientation = Orientation
}
