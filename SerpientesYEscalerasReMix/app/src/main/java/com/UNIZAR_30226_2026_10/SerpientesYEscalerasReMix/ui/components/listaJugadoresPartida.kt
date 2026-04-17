package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Jugador
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fichas_amarillas
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fichas_azules
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fichas_rojas
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fichas_verdes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_primary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_selected
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_transparent
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_unselected

@Composable
fun ListaJugadores(jugadores: List<Jugador>) {
    Column(
        modifier = Modifier
            .width(200.dp)
            .background(color_unselected, RoundedCornerShape(12.dp))
            .border(2.dp, color_primary, RoundedCornerShape(12.dp))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "TURNO",
            style = SETextTypes.mediano,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            jugadores.forEach { jugador ->
                val esTurno = jugador.esTurno ?: false

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(CircleShape)
                        .background(if (esTurno) color_selected else color_bg)
                        .border(1.dp, if (esTurno) color_fg else color_transparent, CircleShape)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
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
                                painter = painterResource(id = jugador.iconoJugador),
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

                    // Obtener el color. Si es null, usamos transparente por defecto.
                    val colorUsuario = jugador.colorFichas ?: Color.Transparent

                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .clip(CircleShape)
                            .background(colorUsuario)
                            .border(1.dp, color_fg.copy(alpha = 0.8f), CircleShape)
                    )

                    Text(
                        text = jugador.nombreJugador ?: "",
                        style = if (esTurno) SETextTypes.plano.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp
                        )
                        else SETextTypes.plano.copy(fontSize = 13.sp),
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
    val equipoActual = "miEquipo"
    val listaJugadores = listOf(
        Jugador(
            nombreJugador = "Ana",
            esTurno = equipoActual == "miEquipo",
            esLider = true,
            iconoJugador = R.drawable.icono_default,
            colorFichas = color_fichas_rojas // Color definido en tu theme
        ),
        Jugador(
            nombreJugador = "Luis",
            esTurno = equipoActual == "equipoAzul",
            esLider = false,
            iconoJugador = R.drawable.icono_default,
            colorFichas = color_fichas_azules
        ),
        Jugador(
            nombreJugador = "Marta",
            esTurno = equipoActual == "equipoVerde",
            esLider = false,
            iconoJugador = R.drawable.icono_default,
            colorFichas = color_fichas_verdes
        ),
        Jugador(
            nombreJugador = "Diego",
            esTurno = equipoActual == "equipoAmarillo",
            esLider = false,
            iconoJugador = R.drawable.icono_default,
            colorFichas = color_fichas_amarillas
        )
    )
    ListaJugadores(listaJugadores)
}