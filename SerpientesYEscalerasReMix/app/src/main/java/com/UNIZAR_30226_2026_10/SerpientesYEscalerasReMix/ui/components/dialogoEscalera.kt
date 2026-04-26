package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_negative
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_positive
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_primary

@Composable
fun DialogoEscalera(
    casillaFinal: Int,
    onAceptar: () -> Unit,
    onRechazar: () -> Unit
) {
    Dialog(onDismissRequest = onRechazar) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.85f)
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
                // Título con el estilo de tus cartas
                Text(
                    text = "¡Has caído en una escalera!",
                    style = SETextTypes.grande,
                    modifier = Modifier.drawBehind {
                        val strokeWidth = 2.dp.toPx()
                        val y = size.height + 4.dp.toPx()
                        drawLine(color_primary, Offset(0f, y), Offset(size.width, y), strokeWidth)
                    }
                )


                // Indicador del destino

                Text(
                    text = "¿Quieres subir a la casilla $casillaFinal?",
                    color = color_primary,
                    textAlign = TextAlign.Center
                )

                // Botones de acción
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Botón Aceptar
                    Button(
                        onClick = onAceptar,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = color_positive),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            "SÍ, SUBIR",
                            style = SETextTypes.plano.copy(color = androidx.compose.ui.graphics.Color.White)
                        )
                    }

                    // Botón Rechazar
                    Button(
                        onClick = onRechazar,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = color_negative),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            "NO, QUEDARME",
                            style = SETextTypes.plano.copy(color = androidx.compose.ui.graphics.Color.White)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewEscalera() {
    DialogoEscalera(45, {}, {})
}