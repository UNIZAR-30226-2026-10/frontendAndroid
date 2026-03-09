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
                    style = SETextTypes.plano, // Estilo más pequeño y grisáceo
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
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
