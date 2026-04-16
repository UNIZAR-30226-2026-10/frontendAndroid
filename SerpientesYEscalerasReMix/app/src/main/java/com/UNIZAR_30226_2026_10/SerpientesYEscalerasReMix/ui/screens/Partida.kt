package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.fakes.fakeTableroSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Carta
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Jugador
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.DadoBoton
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.DetallesCarta
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.ListaJugadores
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.MazoVisual
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.SalirPartidaBoton
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.Tablero
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.rememberSEAppState
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fichas_amarillas
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fichas_azules
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fichas_rojas
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fichas_verdes

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


@Composable
fun Partida(navController: SENavHostController) {
    // Estados para controlar la lógica de la partida
    var cartaSeleccionada by remember { mutableStateOf<Carta?>(null) }
    var mostrarDialogoCarta by remember { mutableStateOf(false) }

    // Estos estados idealmente vendrían de un ViewModel
    val equipoActual = "miEquipo"
    val esMiTurno = true
    val yaJugadoCarta = false


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = color_bg
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            // Tablero (Componente principal)
            Tablero(tableroState = fakeTableroSnapshot)

            // Interfaz Superior: Botón Salir y Lista de Jugadores
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // Botón para salir de la partida
                SalirPartidaBoton(SEState = navController)

                // Lista de jugadores en la partida
                Box(modifier = Modifier.width(200.dp)) {
                    ListaJugadores(listaJugadores)
                }
            }

            // Interfaz Inferior: Mazo de cartas y Botón del Dado
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                // Mazo visual con las cartas del jugador
                Box(modifier = Modifier.width(200.dp)) {
                    MazoVisual(onSelectCarta = { carta ->
                        cartaSeleccionada = carta
                        mostrarDialogoCarta = true
                    })
                }

                // Botón circular para tirar el dado
                DadoBoton()
            }

            // Diálogo de detalles de carta (se activa al pulsar una carta del mazo)
            if (mostrarDialogoCarta && cartaSeleccionada != null) {
                DetallesCarta(
                    carta = cartaSeleccionada!!,
                    esMiTurno = esMiTurno,
                    yaJugadoCarta = yaJugadoCarta,
                    onClose = { mostrarDialogoCarta = false },
                    onJugar = { carta ->
                        println("Jugando carta: ${carta.nombre}")
                        mostrarDialogoCarta = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp,orientation=landscape")
@Composable
fun PartidaScreenPreview() {
    val SEState = rememberSEAppState()
    // Usamos el tema de tu proyecto
    MaterialTheme {
        // PASAMOS LOS DATOS MOCK AQUÍ
        Partida(SEState)
    }
}