package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    DisposableEffect(orientation) {
        val activity = context as? Activity
        val originalOrientation = activity?.requestedOrientation

        // Cambiamos a la orientación deseada
        activity?.requestedOrientation = orientation

        onDispose {
            // Al salir de esta pantalla, restauramos la original (landscape)
            if (originalOrientation != null) {
                activity.requestedOrientation = originalOrientation
            }
        }
    }
}