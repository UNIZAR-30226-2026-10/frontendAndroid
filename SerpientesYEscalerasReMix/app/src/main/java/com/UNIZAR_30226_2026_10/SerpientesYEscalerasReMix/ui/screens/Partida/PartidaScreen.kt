package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Partida

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.ChatBoton
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.DadoBoton
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.DetallesCarta
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.DialogoChat
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.ListaJugadores
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.MazoVisual
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.SalirPartidaBoton
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.Tablero
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.rememberSEAppState
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg

@Composable
fun PartidaScreen(
    navController: SENavHostController,
    viewModel: PartidaViewModel = viewModel()
) {
    // Estado del contenido de la pantalla
    val uiState by viewModel.uiState.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = color_bg
    ) {
        // Usamos un Row para dividir la pantalla en 3 secciones horizontales
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // Salir y Mazo
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f), // Toma el espacio de la izquierda
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                SalirPartidaBoton(SEState = navController)


                Box(modifier = Modifier.width(200.dp)) {
                    MazoVisual(
                        onSelectCarta = { viewModel.onCartaSeleccionada(it) },
                        manoState = uiState.mano
                    )
                }
            }

            // Tablero (Componente principal)
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(2f), // El tablero recibe más espacio
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Tablero(uiState.tablero, uiState.fichas)
            }

            // Jugadores, Chat y Dado
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Lista de jugadores arriba a la derecha
                Box(modifier = Modifier.width(200.dp)) {
                    ListaJugadores(uiState.jugadores)
                }

                // Botón del dado y chat abajo a la derecha
                Box(modifier = Modifier.fillMaxSize()) {
                    // El dado centrado
                    Row(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .offset(x = (-50).dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        DadoBoton({ viewModel.lanzarDado() })
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                            .offset(y = (-5).dp, x = 10.dp)
                    ) {
                        ChatBoton(onClick = { viewModel.cerrarChat() })
                    }
                }
            }
        }

        // Diálogos
        if (uiState.mostrarDialogoCarta) {
            DetallesCarta(
                carta = uiState.cartaEnDetalle,
                esMiTurno = uiState.esMiTurno,
                yaJugadoCarta = uiState.yaJugadoCarta,
                onClose = { viewModel.onCerrarDetalleCarta() },
                onJugar = { viewModel.onJugarCarta(it) }
            )
        }

        if (uiState.mostrarDialogoChat) {
            DialogoChat(
                uiState.chat,
                onClose = { viewModel.cerrarChat() },
                onSend = { viewModel.enviarMsgChat(it) },
            )
        }

        if (uiState.mostrarDialogoEscalera) {
            // TODO añadir DialogoEscalera
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
        PartidaScreen(SEState)
    }
}