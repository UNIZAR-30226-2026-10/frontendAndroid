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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_primary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_secondary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_text

@Composable
fun MazoElegirBoton(nombreMazo: String, onClick: (String) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Surface(
                modifier = Modifier
                    .width(300.dp)
                    .height(400.dp),
                color = color_secondary,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(2.dp, color_primary)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Elegir Mazo",
                        style = SETextTypes.grande,
                        color = color_text,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // TODO: Implementar la recuperación de los mazos
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Lista de mazos no disponible",
                            style = SETextTypes.plano,
                            color = color_text
                        )
                    }
                }
            }
        }
    }

    Surface(
        modifier = Modifier
            .width(120.dp)
            .height(40.dp),
        color = color_secondary,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, color_primary), // Borde amarillo grueso
        shadowElevation = 8.dp,
        onClick = { showDialog = true }
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
