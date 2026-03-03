package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

// Tipografica usada las difetentes partes de nuestra apliación

// Objeto (singleton) que guarda los diferentes tipos de fuentes usadas
object MisEstilos {
    val plano = TextStyle( // Tipografica normal, pasada a MaterialDesign
        color = color_text,
        fontSize = 16.sp
    )

    val menuSeleccionable = TextStyle( // Subrayado y como "plano"
        color = color_text,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        textDecoration = TextDecoration.Underline
    )

    val menuSeleccionado = TextStyle( // Subrayado y gris
        color = color_selectedText,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        textDecoration = TextDecoration.Underline
    )

    val opcionNoDisponible = TextStyle( // gris
        color = color_selectedText,
        fontSize = 16.sp
    )

    val textoMoneda = TextStyle( // Dorado/amarillo
        color = color_SEPText,
        fontSize = 16.sp,
        fontWeight = FontWeight.ExtraBold
    )
}

// Typography estándar (obligatorio para el MaterialTheme)
val Typography = Typography(
    bodyLarge = MisEstilos.plano
)