package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.*

data class Logro(
    val nombre: String,
    val descripcion: String,
    val progresoActual: Int,
    val progresoObjetivo: Int,
    val tipoRecompensa: String,
    val valorRecompensa: String,
    val esCompletado: Boolean = false
)

//Lista de logros de ejemplo (Se eliminará)
val logrosDeEjemplo = listOf(
    Logro(
        nombre = "Jugador Profesional",
        descripcion = "Gana 10 partidas seguidas",
        progresoActual = 7,
        progresoObjetivo = 10,
        tipoRecompensa = "Carta",
        valorRecompensa = "Mago",
        esCompletado = false
    ),
    Logro(
        nombre = "Ventajadicto",
        descripcion = "Utiliza 40 cartas que tengan la categoría buff",
        progresoActual = 40,
        progresoObjetivo = 40,
        tipoRecompensa = "Moneda",
        valorRecompensa = "200",
        esCompletado = true
    ),
    Logro(
        nombre = "Escalador Compulsivo",
        descripcion = "Sube 4 escaleras en una misma partida 20 veces",
        progresoActual = 20,
        progresoObjetivo = 20,
        tipoRecompensa = "Skin",
        valorRecompensa = "Escalera marrón",
        esCompletado = true
    )
)

@Composable
fun LogrosScreen(SEState: SENavHostController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Logros",
            style = SETextTypes.titulo,
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        )
        ListaLogros(logrosDeEjemplo)
    }
}

@Composable
fun ListaLogros(logros: List<Logro>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp), // Espacio en los bordes de la lista
        verticalArrangement = Arrangement.spacedBy(12.dp) // Espacio entre tarjetas
    ) {
        items(logros) { logro ->
            TarjetaLogro(logro)
        }
    }
}

@Composable
fun TarjetaLogro(logro: Logro) {
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
                Text(
                    text = logro.nombre,
                    style = SETextTypes.titulo,
                    color = color_text,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                Text(
                    text = "Descripción:",
                    style = SETextTypes.seleccionable,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                Text(
                    text = "${logro.descripcion}",
                    style = SETextTypes.plano,
                )

                Text(
                    text = "Progreso:",
                    style = SETextTypes.seleccionable,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                Text(
                    text =  "${logro.progresoActual}/${logro.progresoObjetivo}",
                    style = SETextTypes.plano
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .align(Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Recompensa",
                    style = SETextTypes.seleccionable
                )

                if (logro.tipoRecompensa == "Moneda") {
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(
                        text = "${logro.valorRecompensa} Sep",
                        style = SETextTypes.plano
                    )
                } else {

                    Text(
                        text = "${logro.tipoRecompensa}",
                        style = SETextTypes.plano,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}
