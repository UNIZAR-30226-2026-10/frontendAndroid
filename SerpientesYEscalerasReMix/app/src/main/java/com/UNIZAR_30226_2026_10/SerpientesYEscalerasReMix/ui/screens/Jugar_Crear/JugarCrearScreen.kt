package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Jugar_Crear

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.AbandonarLobbyBoton
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.AmigosBoton
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.ContinuarBoton
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.ElegirTableroBoton
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.EmpezarPartidaBoton
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.JugadorItem
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.MazoElegirBoton
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController

@Composable
fun JugarCrearScreen(SEState: SENavHostController, viewModel: JugarCrearViewModel) {

    // Activar polling al entrar en la pantalla
    LaunchedEffect(Unit) {
        if (viewModel.lobbyId.value == "") { // no se esta uniendo a ningún lobby desde la pantalla de Jugar_Amigos
            viewModel.crearLobby()
        }
        viewModel.iniciarPollingLobby()
    }

    // Desactivar polling cuando la pantalla no sea visible
    DisposableEffect(Unit) {
        onDispose {
            viewModel.detenerPollingLobby()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ContinuarBoton(SEState)
            AmigosBoton(SEState)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LobbyElementos(SEState, viewModel)
        }
    }
}

@Composable
fun LobbyElementos(SEState: SENavHostController, viewModel: JugarCrearViewModel) {
    val lobby by viewModel.lobbyActual.collectAsState()
    val vistaLider = viewModel.email.value == lobby?.hostEmail

    val sepVerticalJugadores = 16.dp
    val sepVerticalBotones = 8.dp
    Row(horizontalArrangement = Arrangement.Center) {
        Column(verticalArrangement = Arrangement.spacedBy(sepVerticalJugadores)) {
            JugadorItem(
                vistaLider = vistaLider,
                esLider = lobby?.hostEmail == lobby?.players?.getOrNull(0)?.email,
                esElUsuario = lobby?.players?.getOrNull(0)?.email == viewModel.email.value,
                jugador = lobby?.players?.getOrNull(0),
                onAnadirBot = { viewModel.anadirBot() },
                onExpulsar = {
                    lobby?.players?.getOrNull(0)?.email?.let { email ->
                        viewModel.expulsar(email)
                    }
                }
            )

            JugadorItem(
                vistaLider = vistaLider,
                esLider = lobby?.hostEmail == lobby?.players?.getOrNull(2)?.email,
                esElUsuario = lobby?.players?.getOrNull(2)?.email == viewModel.email.value,
                jugador = lobby?.players?.getOrNull(2),
                onAnadirBot = { viewModel.anadirBot() },
                onExpulsar = {
                    lobby?.players?.getOrNull(2)?.email?.let { email ->
                        viewModel.expulsar(email)
                    }
                }
            )
        }

        Spacer(modifier = Modifier.width(25.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(sepVerticalBotones)
        ) {
            MazoElegirBoton("lateGame")
            if (vistaLider) {
                ElegirTableroBoton(R.drawable.tablero_debug)
            } else {
                Spacer(modifier = Modifier.height(120.dp))
            }
            Row() {
                AbandonarLobbyBoton(SEState, onClick = { viewModel.abandonar() })
                Spacer(modifier = Modifier.width(10.dp))
                // TODO cambiar la condicion de estaListo y todos listos (quizas mas conveniente pasar el viewmodel y hacerlo alli)
                EmpezarPartidaBoton(
                    vistaLider,
                    vistaLider,
                    vistaLider,
                    {},
                    { a -> viewModel.cambiarPreparado(a) }
                )
            }
        }

        Spacer(modifier = Modifier.width(25.dp))

        Column(verticalArrangement = Arrangement.spacedBy(sepVerticalJugadores)) {
            JugadorItem(
                vistaLider = vistaLider,
                esLider = lobby?.hostEmail == lobby?.players?.getOrNull(1)?.email,
                esElUsuario = lobby?.players?.getOrNull(1)?.email == viewModel.email.value,
                jugador = lobby?.players?.getOrNull(1),
                onAnadirBot = { viewModel.anadirBot() },
                onExpulsar = {
                    lobby?.players?.getOrNull(1)?.email?.let { email ->
                        viewModel.expulsar(email)
                    }
                }
            )

            JugadorItem(
                vistaLider = vistaLider,
                esLider = lobby?.hostEmail == lobby?.players?.getOrNull(3)?.email,
                esElUsuario = lobby?.players?.getOrNull(3)?.email == viewModel.email.value,
                jugador = lobby?.players?.getOrNull(3),
                onAnadirBot = { viewModel.anadirBot() },
                onExpulsar = {
                    lobby?.players?.getOrNull(3)?.email?.let { email ->
                        viewModel.expulsar(email)
                    }
                }
            )
        }

        Spacer(modifier = Modifier.width(25.dp))
    }
}
