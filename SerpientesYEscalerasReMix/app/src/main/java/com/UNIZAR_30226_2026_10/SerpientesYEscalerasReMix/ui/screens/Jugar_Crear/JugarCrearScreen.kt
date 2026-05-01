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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.JugadorLobby
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Lobby
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.AbandonarLobbyBoton
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.AmigosBoton
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.ContinuarBoton
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.ElegirTableroBoton
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.EmpezarPartidaBoton
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.JugadorItem
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.MazoElegirBoton
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.rememberSEAppState
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes

@Composable
fun JugarCrearScreen(navController: SENavHostController, viewModel: JugarCrearViewModel) {
    // Activar polling al entrar en la pantalla
    LaunchedEffect(Unit) {
        viewModel.iniciarPolling()
    }

    // Desactivar polling cuando la pantalla no sea visible
    DisposableEffect(Unit) {
        onDispose {
            viewModel.detenerPolling()
        }
    }

    val uiState by viewModel.uiState.collectAsState()

    JugarCrearContent(
        uiState = uiState,
        navController = navController,
        onAnadirBot = { viewModel.onAnadirBot() },
        onExpulsar = { idx -> viewModel.onExpulsar(idx) },
        onAbandonar = { viewModel.onAbandonar() },
        onCambiarListo = { listo -> viewModel.onCambiarListo(listo) },
        onEmpezarPartida = { viewModel.onEmpezarPartida() }
    )
}

@Composable
fun JugarCrearContent(
    uiState: JugarCrearUiState,
    navController: SENavHostController,
    onAnadirBot: () -> Unit,
    onExpulsar: (Int) -> Unit,
    onAbandonar: () -> Unit,
    onCambiarListo: (Boolean) -> Unit,
    onEmpezarPartida: () -> Unit
) {
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
            ContinuarBoton(navController)
            Text(text = "Lobby", style = SETextTypes.titulo)
            AmigosBoton(navController)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LobbyElementos(
                uiState = uiState,
                navController = navController,
                onAnadirBot = onAnadirBot,
                onExpulsar = onExpulsar,
                onAbandonar = onAbandonar,
                onCambiarListo = onCambiarListo,
                onEmpezarPartida = onEmpezarPartida
            )
        }
    }
}

@Composable
fun LobbyElementos(
    uiState: JugarCrearUiState,
    navController: SENavHostController,
    onAnadirBot: () -> Unit,
    onExpulsar: (Int) -> Unit,
    onAbandonar: () -> Unit,
    onCambiarListo: (Boolean) -> Unit,
    onEmpezarPartida: () -> Unit
) {
    val vistaLider = uiState.vistaLider
    val hostEmail = uiState.lobby?.hostEmail ?: ""
    val miEmail = uiState.email

    val miJugador = uiState.lobby?.players?.find { it?.email == miEmail }
    val estaListo = miJugador?.isReady ?: false
    val todosListos = uiState.lobby?.players?.filterNotNull()?.all { it.isReady } ?: false

    val sepVerticalJugadores = 16.dp
    val sepVerticalBotones = 8.dp

    Row(horizontalArrangement = Arrangement.Center) {
        // Columna Izquierda (Jugadores 0 y 2)
        Column(verticalArrangement = Arrangement.spacedBy(sepVerticalJugadores)) {
            val jugador0 = uiState.lobby?.players?.getOrNull(0)
            JugadorItem(
                vistaLider = vistaLider,
                esLider = jugador0?.email == hostEmail,
                esElUsuario = jugador0?.email == miEmail,
                jugador = jugador0?.let { JugadorLobby(it.email, it.username, it.profileIcon, it.isReady, it.isBot, it.deckName) },
                onAnadirBot = onAnadirBot,
                onExpulsar = { onExpulsar(0) }
            )

            val jugador2 = uiState.lobby?.players?.getOrNull(2)
            JugadorItem(
                vistaLider = vistaLider,
                esLider = jugador2?.email == hostEmail,
                esElUsuario = jugador2?.email == miEmail,
                jugador = jugador2?.let { JugadorLobby(it.email, it.username, it.profileIcon, it.isReady, it.isBot, it.deckName) },
                onAnadirBot = onAnadirBot,
                onExpulsar = { onExpulsar(2) }
            )
        }

        Spacer(modifier = Modifier.width(25.dp))

        // Columna Central (Botones y Selección)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(sepVerticalBotones)
        ) {
            MazoElegirBoton(uiState.seleccionMazo.ifEmpty { "Estándar" })
            
            if (vistaLider) {
                ElegirTableroBoton(R.drawable.tablero_debug)
            } else {
                Spacer(modifier = Modifier.height(120.dp))
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                AbandonarLobbyBoton(navController, onClick = onAbandonar)
                Spacer(modifier = Modifier.width(10.dp))
                EmpezarPartidaBoton(
                    esLider = vistaLider,
                    estaListo = estaListo,
                    todosListos = todosListos,
                    onEmpezar = onEmpezarPartida,
                    onCambiarListo = { nuevoEstado -> onCambiarListo(nuevoEstado) }
                )
            }
        }

        Spacer(modifier = Modifier.width(25.dp))

        // Columna Derecha (Jugadores 1 y 3)
        Column(verticalArrangement = Arrangement.spacedBy(sepVerticalJugadores)) {
            val jugador1 = uiState.lobby?.players?.getOrNull(1)
            JugadorItem(
                vistaLider = vistaLider,
                esLider = jugador1?.email == hostEmail,
                esElUsuario = jugador1?.email == miEmail,
                jugador = jugador1?.let { JugadorLobby(it.email, it.username, it.profileIcon, it.isReady, it.isBot, it.deckName) },
                onAnadirBot = onAnadirBot,
                onExpulsar = { onExpulsar(1) }
            )

            val jugador3 = uiState.lobby?.players?.getOrNull(3)
            JugadorItem(
                vistaLider = vistaLider,
                esLider = jugador3?.email == hostEmail,
                esElUsuario = jugador3?.email == miEmail,
                jugador = jugador3?.let { JugadorLobby(it.email, it.username, it.profileIcon, it.isReady, it.isBot, it.deckName) },
                onAnadirBot = onAnadirBot,
                onExpulsar = { onExpulsar(3) }
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 800, heightDp = 480)
@Composable
fun JugarCrearScreenPreview() {
    val mockLobby = Lobby(
        id = "123",
        hostEmail = "host@test.com",
        players = listOf(
            JugadorLobby("host@test.com", "HostUser", "p1", isReady = true, isBot = false),
            JugadorLobby("user2@test.com", "GuestUser", "p2", isReady = false, isBot = false),
            null,
            null
        )
    )
    val mockUiState = JugarCrearUiState(
        lobby = mockLobby,
        vistaLider = true,
        email = "host@test.com",
        seleccionMazo = "Fuego"
    )

    JugarCrearContent(
        uiState = mockUiState,
        navController = rememberSEAppState(),
        onAnadirBot = {},
        onExpulsar = {},
        onAbandonar = {},
        onCambiarListo = {},
        onEmpezarPartida = {}
    )
}
