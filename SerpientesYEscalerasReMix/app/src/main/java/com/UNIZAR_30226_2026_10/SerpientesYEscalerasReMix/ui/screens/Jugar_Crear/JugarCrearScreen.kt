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
import androidx.compose.material3.Text
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
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes

@Composable
fun JugarCrearScreen(SEState: SENavHostController, viewModel: JugarCrearViewModel) {

    // Activar polling al entrar en la pantalla
    LaunchedEffect(Unit) {
        if (viewModel.lobbyId.value == "") { // no se esta uniendo a ningún lobby desde la pantalla de Jugar_Amigos
            viewModel.crearLobby()
        }
        viewModel.iniciarPolling()
    }

    // Desactivar polling cuando la pantalla no sea visible
    DisposableEffect(Unit) {
        onDispose {
            viewModel.detenerPolling()
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
            Text(text = "Lobby", style = SETextTypes.titulo)
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
    val miEmail by viewModel.email.collectAsState()
    val hostEmail = lobby?.hostEmail

    val listaJugadores = lobby?.players ?: emptyList()
    val vistaLider = miEmail == hostEmail

    val sepVerticalJugadores = 16.dp
    val sepVerticalBotones = 8.dp
    Row(horizontalArrangement = Arrangement.Center) {
        Column(verticalArrangement = Arrangement.spacedBy(sepVerticalJugadores)) {
            val jugador0 = listaJugadores.getOrNull(0)
            JugadorItem(
                vistaLider = vistaLider,
                esLider = jugador0?.email == hostEmail,
                esElUsuario = jugador0?.email == miEmail,
                jugador = jugador0,
                onAnadirBot = { viewModel.anadirBot() },
                onExpulsar = { viewModel.expulsar(0) }
            )

            val jugador2 = listaJugadores.getOrNull(2)
            JugadorItem(
                vistaLider = vistaLider,
                esLider = jugador2?.email == hostEmail,
                esElUsuario = jugador2?.email == miEmail,
                jugador = jugador2,
                onAnadirBot = { viewModel.anadirBot() },
                onExpulsar = { viewModel.expulsar(2) }
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
            val jugador1 = listaJugadores.getOrNull(1)
            JugadorItem(
                vistaLider = vistaLider,
                esLider = jugador1?.email == hostEmail,
                esElUsuario = jugador1?.email == miEmail,
                jugador = jugador1,
                onAnadirBot = { viewModel.anadirBot() },
                onExpulsar = { viewModel.expulsar(1) }
            )

            val jugador3 = listaJugadores.getOrNull(3)
            JugadorItem(
                vistaLider = vistaLider,
                esLider = jugador3?.email == hostEmail,
                esElUsuario = jugador3?.email == miEmail,
                jugador = jugador3,
                onAnadirBot = { viewModel.anadirBot() },
                onExpulsar = { viewModel.expulsar(3) }
            )
        }

        Spacer(modifier = Modifier.width(25.dp))
    }
}
