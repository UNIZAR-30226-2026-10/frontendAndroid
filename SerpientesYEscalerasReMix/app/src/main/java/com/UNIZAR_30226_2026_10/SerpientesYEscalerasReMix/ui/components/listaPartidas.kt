package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

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
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.RegistroPartida
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.Destinos
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_primary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_secondary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_text

@Composable
fun ListaPartidas(
    navHost: SENavHostController?,
    partidas: List<RegistroPartida>,
    onTarjeta: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(partidas) { partida ->
            TarjetaPartida(navHost, partida, onTarjeta)
        }
    }
}

@Composable
fun TarjetaPartida(
    navHost: SENavHostController?,
    partida: RegistroPartida,
    onTarjeta: (String) -> Unit
) {
    Surface(
        color = color_secondary,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, color_primary),
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            onTarjeta(partida.id)
            navHost?.goTo(Destinos.PARTIDA)
        }
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Fecha de inicio y Nombre del Mapa
                    Text(text = "Partida del ${partida.fecha} | ${partida.mapa}", style = SETextTypes.plano, color = color_text)
                }

                // Participantes
                Text(
                    text = "Participantes: ${partida.jugadores}",
                    style = SETextTypes.sombreado,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Continuar", style = SETextTypes.plano, textDecoration = TextDecoration.Underline)
                Icon(Icons.Default.PlayArrow, null, modifier = Modifier.size(16.dp), tint = color_text)
            }
        }
    }
}

/*@Preview(showBackground = true, widthDp = 800, heightDp = 400)
@Composable
fun PreviewListaPartidas() {
    val partidasEjemplo = listOf(
        RegistroPartida("2023-09-20", "Mapa1", "Jugador1, Jugador2", "1"),
        RegistroPartida("2023-09-21", "Mapa2", "Jugador3, Jugador4", "2"),
    )
    ListaPartidas(navHost = null, partidas = partidasEjemplo, onTarjeta = {})
}*/