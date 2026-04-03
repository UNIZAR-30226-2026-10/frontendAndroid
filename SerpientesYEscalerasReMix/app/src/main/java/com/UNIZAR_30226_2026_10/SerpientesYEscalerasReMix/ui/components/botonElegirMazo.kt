package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_primary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_secondary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_text

@Composable
fun MazoElegirBoton(nombreMazo: String) {
    Surface(
        modifier = Modifier
            .width(120.dp)
            .height(40.dp),
        color = color_secondary,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, color_primary), // Borde amarillo grueso
        shadowElevation = 8.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Mazo", style = SETextTypes.grande)
                Text(nombreMazo, style = SETextTypes.plano)
            }

            // El icono de flecha a la derecha
            Icon(
                imageVector = Icons.Default.PlayArrow, // O uno similar
                contentDescription = null,
                tint = color_text,
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterEnd) // Se alinea a la derecha del Box
                    .offset(x = 8.dp)
            )
        }
    }
}