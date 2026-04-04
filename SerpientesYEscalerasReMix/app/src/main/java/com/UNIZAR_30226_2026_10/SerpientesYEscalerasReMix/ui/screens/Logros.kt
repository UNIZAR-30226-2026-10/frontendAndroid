package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
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
    val esCompletado: Boolean = false,
    val imagen: Int = R.drawable.tablero_debug
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
            verticalAlignment = Alignment.Top
        ) {
            // Columna de información (Izquierda)
            Column(modifier = Modifier.weight(1f)) {
                if (logro.esCompletado) {
                    Text(
                        text = logro.nombre + " (Completado)",
                        style = SETextTypes.titulo,
                        color = color_text
                    )
                } else {
                    Text(
                        text = logro.nombre,
                        style = SETextTypes.titulo,
                        color = color_text
                    )
                }

                Text(
                    text = "Descripción:",
                    style = SETextTypes.seleccionable,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                Text(
                    text = logro.descripcion,
                    style = SETextTypes.plano,
                )

                Text(
                    text = "Progreso:",
                    style = SETextTypes.seleccionable,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                if (logro.esCompletado) {
                    Text(
                        text =  "(${logro.progresoActual}/${logro.progresoObjetivo})",
                        style = SETextTypes.sombreado
                    )
                } else {
                    Text(
                        text = "(${logro.progresoActual}/${logro.progresoObjetivo})",
                        style = SETextTypes.plano
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .align(Alignment.Top)
                    .width(80.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Recompensa",
                    style = SETextTypes.seleccionable
                )

                if (logro.tipoRecompensa == "Moneda") {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${logro.valorRecompensa} Sep",
                            style = SETextTypes.plano,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.height(4.dp))

                    //Caja en la que se encontrará la imagen de la recompensa (carta o skin)
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .aspectRatio(3f / 4f)
                            .background(color_selectedText),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = logro.imagen),
                            contentDescription = "Foto de la recompensa: ${logro.tipoRecompensa}",
                            modifier = Modifier.fillMaxHeight(),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = logro.tipoRecompensa,
                        style = SETextTypes.plano,
                    )
                }
            }
        }
    }
}
