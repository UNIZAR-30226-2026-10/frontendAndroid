package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.eleccionCrearContinuar
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_primary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_secondary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_text

// Clase a eliminar de este fichero (en relación con viewModel y clases de retrofit), modificar con los campos pertinentes
data class Partida(
    val nombre: String,
    val fecha: String,
    val turno: Int,
    val jugadores: String
)

val partidasDeEjemplo = listOf(
    Partida(
        nombre = "Partida de Escalador Maestro",
        fecha = "2 / 1 / 2026",
        turno = 46,
        jugadores = "Escalador Maestro, ZigZagKing, Colmillo Veloz y Escalera77"
    ),
    Partida(
        nombre = "Duelo de Serpientes",
        fecha = "3 / 1 / 2026",
        turno = 3,
        jugadores = "Escalador Maestro, ZigZagKing, Colmillo Veloz y Escalera77"
    ),
    Partida(
        nombre = "Torneo Nocturno",
        fecha = "5 / 1 / 2026",
        turno = 12,
        jugadores = "PythonPro, Anaconda99 y Escalador Maestro"
    ),
    Partida(
        nombre = "Partida Rápida",
        fecha = "09 / 03 / 2026",
        turno = 1,
        jugadores = "Solo contra la IA"
    )
)

@Composable
fun JugarContinuarScreen(SEState: SENavHostController) {
    var opcionSeleccionada = "Continuar"

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        eleccionCrearContinuar(opcionSeleccionada, SEState)

        ListaPartidas(partidasDeEjemplo)
    }
}

@Composable
fun ListaPartidas(partidas: List<Partida>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp), // Espacio en los bordes de la lista
        verticalArrangement = Arrangement.spacedBy(12.dp) // Espacio entre tarjetas
    ) {
        items(partidas) { partida ->
            TarjetaPartida(partida)
        }
    }
}

@Composable
fun TarjetaPartida(partida: Partida) {
    Surface(
        color = color_secondary,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, color_primary), // Borde amarillo/dorado
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Columna de información (Izquierda)
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = partida.nombre, style = SETextTypes.plano, color = color_text)
                    Text(text = partida.fecha, style = SETextTypes.plano, color = color_text)
                }

                Text(
                    text = "Turno ${partida.turno}",
                    style = SETextTypes.plano,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                Text(
                    text = partida.jugadores,
                    style = SETextTypes.sombreado,
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Botón Continuar (Derecha)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Continuar",
                    style = SETextTypes.plano,
                    textDecoration = TextDecoration.Underline
                )
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = color_text
                )
            }
        }
    }
}
