package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_primary

@Composable
fun DialogoBifurcacion(
    casillaA: Int,
    casillaB: Int,
    onDecision: (Int) -> Unit
) {
    // DialogProperties evita que se cierre al pulsar fuera o atrás
    Dialog(
        onDismissRequest = { /* No hacer nada para forzar la elección */ },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentSize(),
            color = color_bg,
            border = BorderStroke(3.dp, color_primary),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Título descriptivo
                Text(
                    text = "BIFURCACIÓN",
                    style = SETextTypes.grande,
                    modifier = Modifier.drawBehind {
                        val strokeWidth = 2.dp.toPx()
                        val y = size.height + 4.dp.toPx()
                        drawLine(color_primary, Offset(0f, y), Offset(size.width, y), strokeWidth)
                    }
                )

                Text(
                    text = "Has llegado una Bifurcación\n¿Qué ruta deseas tomar?",
                    style = SETextTypes.plano,
                    color = color_primary,
                    textAlign = TextAlign.Center
                )

                // Botones
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { onDecision(casillaA) },
                        modifier = Modifier.weight(1f), // Divide el espacio equitativamente
                        colors = ButtonDefaults.buttonColors(containerColor = color_primary),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(4.dp)
                    ) {
                        Text(
                            text = "Hacia la casilla $casillaA",
                            style = SETextTypes.plano,
                            textAlign = TextAlign.Center
                        )
                    }

                    Button(
                        onClick = { onDecision(casillaB) },
                        modifier = Modifier.weight(1f), // Divide el espacio equitativamente
                        colors = ButtonDefaults.buttonColors(containerColor = color_primary),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(4.dp)
                    ) {
                        Text(
                            text = "Hacia la casilla $casillaB",
                            style = SETextTypes.plano,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewBifurcacion() {
    DialogoBifurcacion(
        casillaA = 45,
        casillaB = 52,
        onDecision = { elegida -> println("Ir a $elegida") }
    )
}