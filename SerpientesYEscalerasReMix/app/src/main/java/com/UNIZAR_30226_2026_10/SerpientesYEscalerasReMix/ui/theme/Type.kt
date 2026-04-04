package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

// Tipografica usada en las difetentes partes de nuestra apliación

// Objeto (singleton) que guarda los diferentes tipos de fuentes usadas
object SETextTypes {
    val plano = TextStyle( // Tipografica normal, pasada a MaterialDesign
        color = color_text,
        fontSize = 12.sp
    )

    val grande = TextStyle( // Tipografica normal, pasada a MaterialDesign
        color = color_text,
        fontSize = 18.sp
    )

    val titulo = TextStyle( // Tipografica normal, pasada a MaterialDesign
        color = color_text,
        fontSize = 30.sp,
        fontWeight = FontWeight.ExtraBold,
        // Centrar texto
        textAlign = androidx.compose.ui.text.style.TextAlign.Center
    )

    val tab = TextStyle( // Tipografica normal, pasada a MaterialDesign
        color = color_text,
        fontSize = 25.sp
    )

    val subtitulo = TextStyle( // Tipografia para subtitulos (grande)
        color = color_text,
        fontSize = 25.sp
    )

    val seleccionable = TextStyle( // Subrayado y como "plano"
        color = color_text,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        textDecoration = TextDecoration.Underline
    )

    val seleccionado = TextStyle( // Subrayado y gris
        color = color_selectedText,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        textDecoration = TextDecoration.Underline
    )

    val sombreado = TextStyle( // gris
        color = color_selectedText,
        fontSize = 12.sp
    )

    val SEPStyle = TextStyle( // Dorado/amarillo
        color = color_SEPText,
        fontSize = 13.sp,
        fontWeight = FontWeight.ExtraBold
    )
}

// Typography estándar (obligatorio para el MaterialTheme)
val Typography = Typography(
    bodyLarge = SETextTypes.plano
)