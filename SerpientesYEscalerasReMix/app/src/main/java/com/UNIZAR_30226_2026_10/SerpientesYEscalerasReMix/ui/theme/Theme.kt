package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

// Definición de temas por defecto
private val SerpientesYEscalerasColors = darkColorScheme(
    primary = color_primary,
    background = color_bg,
    surface = color_sf,
    onBackground = color_text,
    onSurface = color_text
)

@Composable
fun SerpientesYEscalerasReMixTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SerpientesYEscalerasColors,
        typography = Typography,
        content = content
    )
}