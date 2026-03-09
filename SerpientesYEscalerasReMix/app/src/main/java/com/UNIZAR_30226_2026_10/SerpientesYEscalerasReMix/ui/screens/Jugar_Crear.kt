package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.JugadorItem
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.eleccionCrearContinuar
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_primary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_secondary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_selected
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_text

@Composable
fun JugarCrearScreen(SEState: SENavHostController) {
    var opcionSeleccionada = "Crear"

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        eleccionCrearContinuar(opcionSeleccionada, SEState)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))

            lobby()

            Spacer(modifier = Modifier.width(16.dp))

            amigos()
        }
    }
}

@Composable
fun lobby() {
    val sepVerticalJugadores = 16.dp
    val sepVerticalBotones = 8.dp
    Row(horizontalArrangement = Arrangement.Center) {
        Column(verticalArrangement = Arrangement.spacedBy(sepVerticalJugadores)) {
            JugadorItem(R.drawable.icono_default, "default", "Tú", true)
            JugadorItem(R.drawable.icono_default, "default", "Yo", false)
        }

        Spacer(modifier = Modifier.width(25.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(sepVerticalBotones)
        ) {
            BotonMazo("lateGame")
            elegirTablero(R.drawable.tablero_debug)
            EmpezarPartida()
        }

        Spacer(modifier = Modifier.width(25.dp))

        Column(verticalArrangement = Arrangement.spacedBy(sepVerticalJugadores)) {
            JugadorItem(R.drawable.icono_default, "default", "", true)
            JugadorItem(R.drawable.icono_default, "default", "", false)
        }

        Spacer(modifier = Modifier.width(25.dp))
    }
}

@Composable
fun BotonMazo(nombreMazo: String) {
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

@Composable
fun elegirTablero(tablero: Int) {
    Surface(
        modifier = Modifier
            .size(120.dp),
        color = color_secondary,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, color_primary) // Borde amarillo grueso
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título superior
            Text(
                text = "Tablero",
                modifier = Modifier.padding(vertical = 4.dp),
                style = SETextTypes.grande
            )

            // Contenedor de la imagen e icono
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.background(color = color_bg)
            ) {
                // Imagen del tablero (reemplaza con tu recurso)
                Image(
                    painter = painterResource(id = tablero),
                    contentDescription = "Tablero de juego",
                    modifier = Modifier
                        .height(80.dp)
                        .width(80.dp)
                        .alpha(0.8f),
                    contentScale = ContentScale.Crop,
                )

                // Icono de edición (el lápiz blanco)
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar",
                    tint = color_text,
                    modifier = Modifier
                        .size(60.dp)
                )
            }
        }
    }
}

@Composable
fun EmpezarPartida() {
    Surface(
        modifier = Modifier
            .width(150.dp)
            .height(40.dp),
        color = color_selected,
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 8.dp
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Empezar Partida",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = SETextTypes.grande
            )
        }
    }
}

@Composable
fun amigos() {

    Surface(
        color = color_secondary,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, color_primary) // Borde amarillo grueso
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(4.dp)) {
            Image(
                painter = painterResource(id = R.drawable.amigos),
                contentDescription = "amigos",
                modifier = Modifier
                    .size(50.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = CircleShape,
                        clip = false
                    )
                    .clip(CircleShape)
                    .border(1.dp, color_bg, CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text("Amigos", style = SETextTypes.plano)

            Spacer(modifier = Modifier.width(8.dp))

            // El icono de flecha a la derecha
            Icon(
                imageVector = Icons.Default.PlayArrow, // O uno similar
                contentDescription = null,
                tint = color_text,
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterVertically) // Se alinea a la derecha del Box
            )
        }

    }
}
