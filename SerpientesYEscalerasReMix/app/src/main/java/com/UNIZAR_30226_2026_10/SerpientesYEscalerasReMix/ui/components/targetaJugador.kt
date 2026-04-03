package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.JugadorLobby
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.buscarIconoR
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_positive
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_sf
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_transparent


@Composable
fun JugadorItem(
    vistaLider: Boolean,
    esLider: Boolean,
    jugador: JugadorLobby?,
    onAnadirBot: () -> Unit,
    onExpulsar: () -> Unit
) {
    var distanciaIcono = 22.dp
    var offsetIcono = -44.dp

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(top = distanciaIcono)
    ) {
        Surface(
            shape = RoundedCornerShape(18.dp),
            color = color_sf,
            modifier = Modifier
                .sizeIn(maxWidth = 200.dp, maxHeight = 300.dp)
                .width(120.dp)
                .height(80.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                if (jugador != null) {
                    Text(
                        text = jugador.username,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = SETextTypes.grande
                    )
                } else if (vistaLider) {
                    AnadirBot(onAnadirBot)
                }
            }
        }

        if (jugador != null) {
            val icono = if (jugador.isBot) buscarIconoR("bot")
            else buscarIconoR(jugador.profileIcon)

            Box(
                modifier = Modifier.offset(y = offsetIcono)
            ) {
                JugadorIcon(icono, esLider)
            }
        }

        if (jugador != null && jugador.isReady) {
            IndicadorListo()
        }

        if (jugador != null && vistaLider) {
            ExpulsarBoton(onExpulsar)
        }
    }
}

@Composable
fun AnadirBot(onAnadirBot: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(onClick = onAnadirBot, color = color_transparent) {
            Image(
                painter = painterResource(id = R.drawable.plus_simbol),
                contentDescription = "icono Añadir",
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
            )

            Text("Añadir Bot", style = SETextTypes.grande)
        }
    }
}

@Composable
fun JugadorIcon(icono: Int, esLider: Boolean) {
    Box(contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .size(62.dp)
                .blur(1.dp)
                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
        )

        Image(
            painter = painterResource(id = icono),
            contentDescription = "icono jugador",
            modifier = Modifier
                .size(60.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = CircleShape,
                    clip = false
                )
                .clip(CircleShape)
                .border(1.dp, color_bg, CircleShape)
        )

        if (esLider) {
            Image(
                painter = painterResource(id = R.drawable.corona),
                contentDescription = "corona del lider del lobby",
                modifier = Modifier
                    .size(40.dp)
                    .graphicsLayer(
                        scaleX = -1f, // Espejo horizontal
                        rotationZ = 40f
                    )
                    .offset(y = -40.dp)
            )
        }
    }
}

@Composable
fun IndicadorListo() {
    Box(
        modifier = Modifier
            .size(28.dp)
            // Posicionamos en la esquina superior derecha del Surface
            .offset(x = 50.dp, y = (-35).dp)
            .background(color_positive, CircleShape)
            .border(2.dp, color_fg, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Check, // Usamos el icono Check estándar
            contentDescription = "Jugador Listo",
            tint = color_fg,
            modifier = Modifier.size(20.dp) // Tamaño del icono dentro del círculo
        )
    }
}

@Composable
fun ExpulsarBoton(onExpulsar: () -> Unit) {
    Surface(onClick = onExpulsar, color = color_transparent) {
        Box(
            modifier = Modifier
                // Posicionamos en la esquina inferior derecha del Surface
                .offset(x = 50.dp, y = 35.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.icono_expulsar),
                contentDescription = "icono expulsar",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
