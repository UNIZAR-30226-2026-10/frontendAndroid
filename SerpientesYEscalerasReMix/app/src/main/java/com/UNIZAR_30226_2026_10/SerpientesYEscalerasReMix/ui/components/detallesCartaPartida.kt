package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Carta
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_negative
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_positive
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_primary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_selected
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_unselected

@Composable
fun DetallesCarta(
    carta: Carta?,
    esMiTurno: Boolean,
    yaJugadoCarta: Boolean,
    onClose: () -> Unit,
    onJugar: (Carta) -> Unit
) {
    Dialog(onDismissRequest = onClose) {
        CartaContent(carta, esMiTurno, yaJugadoCarta, onJugar)
    }
}

@Composable
fun CartaContent(
    carta: Carta?,
    esMiTurno: Boolean,
    yaJugadoCarta: Boolean,
    onJugar: (Carta) -> Unit
) {
    if (carta == null) return
    val puedeJugar = esMiTurno && !yaJugadoCarta

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight()
            .border(5.dp, color_primary, RoundedCornerShape(24.dp)),
        colors = CardDefaults.cardColors(containerColor = color_bg),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Título
            Text(
                text = carta.nombre.uppercase(),
                style = SETextTypes.grande,
                modifier = Modifier.drawBehind {
                    val strokeWidth = 2.dp.toPx()
                    val y = size.height + 4.dp.toPx()
                    drawLine(color_selected, Offset(0f, y), Offset(size.width, y), strokeWidth)
                }
            )

            // Imagen de la carta
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = carta.imagen),
                    contentDescription = "imagen carta",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit // IMPORTANTE: No se corta, se ajusta
                )
            }

            // Efecto
            Surface(
                color = color_unselected.copy(alpha = 0.6f),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, color_selected.copy(alpha = 0.5f))
            ) {
                Text(
                    text = "\"${carta.efecto}\"",
                    style = SETextTypes.plano.copy(fontStyle = FontStyle.Italic),
                    modifier = Modifier.padding(12.dp),
                    textAlign = TextAlign.Center
                )
            }

            // Botón de acción
            if (puedeJugar) {
                Button(
                    onClick = { onJugar(carta) },
                    colors = ButtonDefaults.buttonColors(containerColor = color_positive),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("UTILIZAR CARTA", style = SETextTypes.plano.copy(fontWeight = FontWeight.Bold))
                }
            } else {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = color_negative.copy(alpha = 0.1f),
                    border = BorderStroke(1.dp, color_negative.copy(alpha = 0.5f)),
                    shape = CircleShape
                ) {
                    Text(
                        text = if (!esMiTurno) "No es tu turno" else "Ya has usado una carta este turno",
                        style = SETextTypes.sombreado.copy(color = color_negative, fontSize = 10.sp),
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetallesPrev() {
    val carta = Carta(
        id = 1,
        nombre = "moises",
        imagen = R.drawable.carta_moises,
        efecto = "Divide las aguas y avanza casillas -- saltate un bloqueo"
    )

    CartaContent(
        carta = carta,
        esMiTurno = true,
        yaJugadoCarta = false,
        onJugar = { _ -> }
    )

}