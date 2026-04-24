package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.fakes.fakeJugadoresSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.JugadoresSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_primary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_selected
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_selectedText
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_transparent
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_unselected

@Composable
fun ListaJugadores(
    jugadoresState: JugadoresSnapshot,
    seleccionCarta: Boolean,
    onSeleccionCarta: (String) -> Unit
) {

    // Animacion para la seleccion si seleccionCarta
    val transition = rememberInfiniteTransition(label = "parpadeo")
    val alphaAnimado by transition.animateFloat(
        initialValue = 0.5f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Column(
        modifier = Modifier
            .width(200.dp)
            .background(color_unselected, RoundedCornerShape(12.dp))
            .border(2.dp, color_primary, RoundedCornerShape(12.dp))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "RONDA ${jugadoresState.ronda}",
            style = SETextTypes.mediano,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            var numJugador: Int = 0
            jugadoresState.jugadores.forEach { jugador ->
                numJugador++
                val esTurno = numJugador == jugadoresState.turno

                val colorJugadorFondo =
                    if(seleccionCarta) color_selectedText.copy(alpha = alphaAnimado)
                    else if (esTurno) color_selected
                    else color_unselected

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(CircleShape)
                        .background(colorJugadorFondo)
                        .border(1.dp, if (esTurno && !seleccionCarta) color_fg else color_transparent, CircleShape)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .then( // seleccionar jugador para accion de una carta
                            if (seleccionCarta) {
                                Modifier
                                    .clickable { onSeleccionCarta(jugador.email) }
                            } else Modifier
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Imagen de perfil y Corona
                    Box(contentAlignment = Alignment.Center) {
                        Surface(
                            shape = CircleShape,
                            color = color_fg,
                            modifier = Modifier.size(24.dp),
                            border = BorderStroke(1.dp, color_bg)
                        ) {
                            Image(
                                painter = painterResource(id = jugador.icono),
                                contentDescription = "Perfil Snake",
                                modifier = Modifier.padding(4.dp),
                                contentScale = ContentScale.Fit
                            )
                        }
                        if (jugador.esLider == true) {
                            Image(
                                painter = painterResource(id = R.drawable.corona),
                                contentDescription = "corona del lider del lobby",
                                modifier = Modifier
                                    .size(15.dp)
                                    .graphicsLayer(
                                        scaleX = -1f, // Espejo horizontal
                                        rotationZ = 40f
                                    )
                                    .offset(y = (-13).dp)
                            )

                        }
                    }

                    // Icono correspondiente a la skin
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .clip(CircleShape)
                            .background(jugador.color)
                            .border(1.dp, color_fg.copy(alpha = 0.8f), CircleShape)
                    )

                    Text(
                        text = jugador.nombre,
                        style = SETextTypes.plano.copy(fontSize = 13.sp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListaPrev() {
    ListaJugadores(fakeJugadoresSnapshot, true, onSeleccionCarta = {a -> })
}