package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.JugadorItem
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_selected

@Preview
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
        modifier = Modifier.fillMaxWidth(),
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

        empezarPartida()

        Spacer(modifier = Modifier.width(50.dp))

        Column(verticalArrangement = Arrangement.spacedBy(sepVerticalJugadores)) {
            JugadorItem(R.drawable.icono_default, "default", "")
            JugadorItem(R.drawable.icono_default, "default", "")
        }
    }
}

@Composable
fun empezarPartida() {
    Surface(
        modifier = Modifier.fillMaxWidth().height(100.dp),
        color = color_selected,
        shape = RoundedCornerShape(18.dp),
        shadowElevation = 8.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text("Empezar Partida")
        }
    }
}
