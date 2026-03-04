package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens

import android.graphics.Paint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.JugadorItem
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_primary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_secondary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_selected
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_text

@Preview(
    device = "spec:width=891dp,height=411dp,orientation=landscape,dpi=440"
)
@Composable
fun JugarScreen() {
    var opcionSeleccionada by remember { mutableStateOf("Crear") }
    Column() {
        eleccionCrearContinuar(opcionSeleccionada)
        lobby()
    }

}

@Composable
fun eleccionCrearContinuar(opcion: String) {
    val crearStyle = if (opcion == "Crear") SETextTypes.seleccionado
                     else SETextTypes.seleccionable

    var continuarStyle = if (opcion != "Crear") SETextTypes.seleccionado
                         else SETextTypes.seleccionable

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text("Crear Partida", style = crearStyle)
        Spacer(modifier = Modifier.width(30.dp))
        Text("Continuar Partida", style = continuarStyle)
    }

}

@Composable
fun lobby() {
    val sepVerticalJugadores = 16.dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(sepVerticalJugadores)) {
            JugadorItem(R.drawable.icono_default, "default", "Tú")
            JugadorItem(R.drawable.icono_default, "default", "")
        }

        Spacer(modifier = Modifier.width(50.dp))

        Column(verticalArrangement = Arrangement.spacedBy(sepVerticalJugadores)) {
            BotonMazo("lateGame")
            EmpezarPartida()
        }



        Spacer(modifier = Modifier.width(50.dp))

        Column(verticalArrangement = Arrangement.spacedBy(sepVerticalJugadores)) {
            JugadorItem(R.drawable.icono_default, "default", "")
            JugadorItem(R.drawable.icono_default, "default", "")
        }
    }
}

@Composable
fun BotonMazo(nombreMazo: String) {
    Surface(
        modifier = Modifier
            .width(120.dp)
            .height(30.dp),
        color = color_secondary,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(3.dp, color_primary), // Borde amarillo grueso
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
                Text(nombreMazo, style = SETextTypes.grande)
            }

            // El icono de flecha a la derecha
            Icon(
                imageVector = Icons.Default.PlayArrow, // O uno similar
                contentDescription = null,
                tint = color_text,
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterEnd) // Se alinea a la derecha del Box
            )
        }
    }
}

@Composable
fun EmpezarPartida() {
    Surface(
        modifier = Modifier
            .width(120.dp)
            .height(30.dp),
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
