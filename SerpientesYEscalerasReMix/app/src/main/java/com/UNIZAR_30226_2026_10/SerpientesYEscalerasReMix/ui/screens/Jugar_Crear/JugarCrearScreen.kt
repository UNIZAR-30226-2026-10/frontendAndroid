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
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.JugadorLobby
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Lobby
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.buscarMiniaturaTableroR
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.AbandonarLobbyBoton
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.AmigosBoton
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.ContinuarBoton
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.ElegirTableroBoton
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.EmpezarPartidaBoton
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.JugadorItem
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.MazoElegirBoton
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.Destinos
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.rememberSEAppState
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun JugarCrearScreen(navController: SENavHostController, viewModel: JugarCrearViewModel) {
    // Activar polling al entrar en la pantalla
    LaunchedEffect(Unit) {
        viewModel.iniciarPolling { navController.goTo(Destinos.PARTIDA) }
        viewModel.obtenerTableros()
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
        seleccionMazo = viewModel.seleccionMazo,
        navController = navController,
        onAnadirBot = { viewModel.onAnadirBot() },
        onExpulsar = { idx -> viewModel.onExpulsar(idx) },
        onAbandonar = { viewModel.onAbandonar() },
        onCambiarListo = { listo -> viewModel.onCambiarListo(listo) },
        onEmpezarPartida = { viewModel.onEmpezarPartida( { navController.goTo(Destinos.PARTIDA) } ) },
        onElegirTablero = { tablero -> viewModel.onSeleccionarTablero(tablero) },
        onElegirMazo = { mazo -> viewModel.onSeleccionarMazo(mazo) },
        tableroSeleccionado = uiState.seleccionTablero
    )
}

@Composable
fun JugarCrearContent(
    uiState: JugarCrearUiState,
    seleccionMazo: Flow<String>,
    navController: SENavHostController,
    onAnadirBot: () -> Unit,
    onExpulsar: (Int) -> Unit,
    onAbandonar: () -> Unit,
    onCambiarListo: (Boolean) -> Unit,
    onEmpezarPartida: () -> Unit,
    onElegirTablero: (String) -> Unit,
    onElegirMazo: (String) -> Unit,
    tableroSeleccionado: String
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
                seleccionMazo = seleccionMazo,
                navController = navController,
                onAnadirBot = onAnadirBot,
                onExpulsar = onExpulsar,
                onAbandonar = onAbandonar,
                onCambiarListo = onCambiarListo,
                onEmpezarPartida = onEmpezarPartida,
                onElegirTablero = onElegirTablero,
                onElegirMazo = onElegirMazo,
                tableroSeleccionado = tableroSeleccionado
            )
        }
    }
}

@Composable
fun LobbyElementos(
    uiState: JugarCrearUiState,
    seleccionMazo: Flow<String>,
    navController: SENavHostController,
    onAnadirBot: () -> Unit,
    onExpulsar: (Int) -> Unit,
    onAbandonar: () -> Unit,
    onCambiarListo: (Boolean) -> Unit,
    onEmpezarPartida: () -> Unit,
    onElegirTablero: (String) -> Unit,
    onElegirMazo: (String) -> Unit,
    tableroSeleccionado: String
) {
    val vistaLider = uiState.vistaLider
    val hostUsername = uiState.lobby?.hostUsername ?: ""
    val username = uiState.username

    val miJugador = uiState.lobby?.players?.find { it?.username == username }
    val estaListo = miJugador?.isReady ?: false
    val todosListos = uiState.lobby?.players?.filterNotNull()?.all { it.isReady || it.username == username } ?: false
    val mazoActual by seleccionMazo.collectAsState(initial = "")

    val sepVerticalJugadores = 16.dp
    val sepVerticalBotones = 8.dp

    Row(horizontalArrangement = Arrangement.Center) {
        // Columna Izquierda (Jugadores 0 y 2)
        Column(verticalArrangement = Arrangement.spacedBy(sepVerticalJugadores)) {
            val jugador0 = uiState.lobby?.players?.getOrNull(0)
            JugadorItem(
                vistaLider = vistaLider,
                esLider = jugador0?.username == hostUsername,
                esElUsuario = jugador0?.username == username,
                jugador = jugador0,
                onAnadirBot = onAnadirBot,
                onExpulsar = { onExpulsar(0) }
            )

            val jugador2 = uiState.lobby?.players?.getOrNull(2)
            JugadorItem(
                vistaLider = vistaLider,
                esLider = jugador2?.username == hostUsername,
                esElUsuario = jugador2?.username == username,
                jugador = jugador2,
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
            MazoElegirBoton(seleccionMazo, onClick = onElegirMazo)
            
            if (vistaLider) {
                ElegirTableroBoton(
                    tableroResId = buscarMiniaturaTableroR(tableroSeleccionado),
                    nombreTableros = uiState.nombreTableros,
                    onClick = { tablero -> onElegirTablero(tablero) })
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
                    mazoSeleccionado = mazoActual.isNotEmpty(),
                    onEmpezar = onEmpezarPartida,
                    onCambiarListo = { nuevoEstado -> onCambiarListo(!nuevoEstado) }
                )
            }
        }

        Spacer(modifier = Modifier.width(25.dp))

        // Columna Derecha (Jugadores 1 y 3)
        Column(verticalArrangement = Arrangement.spacedBy(sepVerticalJugadores)) {
            val jugador1 = uiState.lobby?.players?.getOrNull(1)
            JugadorItem(
                vistaLider = vistaLider,
                esLider = jugador1?.username == hostUsername,
                esElUsuario = jugador1?.username == username,
                jugador = jugador1,
                onAnadirBot = onAnadirBot,
                onExpulsar = { onExpulsar(1) }
            )

            val jugador3 = uiState.lobby?.players?.getOrNull(3)
            JugadorItem(
                vistaLider = vistaLider,
                esLider = jugador3?.username == hostUsername,
                esElUsuario = jugador3?.username == username,
                jugador = jugador3,
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
        hostUsername = "host@test.com",
        players = listOf(
            JugadorLobby("host", "default", true, isBot = false),
            JugadorLobby("user2", "default", false, isBot = false),
            JugadorLobby("bot1", "default", true, isBot = false),
            null
        ),
        tableroSelect = "Estándar",
        null
    )
    val mockUiState = JugarCrearUiState(
        lobby = mockLobby,
        vistaLider = true,
        username = "host@test.com"
    )

    JugarCrearContent(
        uiState = mockUiState,
        seleccionMazo = flowOf("Fuego"),
        navController = rememberSEAppState(),
        onAnadirBot = {},
        onExpulsar = {},
        onAbandonar = {},
        onCambiarListo = {},
        onEmpezarPartida = {},
        onElegirTablero = {},
        onElegirMazo = {},
        tableroSeleccionado = "Estándar"
    )
}
