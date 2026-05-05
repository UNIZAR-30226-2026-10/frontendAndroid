package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Jugar_Amigos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.Usuario
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.CabeceraAmigos
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.ListaAmigos
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.Destinos
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.rememberSEAppState
import kotlinx.coroutines.launch

@Composable
fun AmigosScreen(navHost: SENavHostController, snackHost: SnackbarHostState, viewModel: AmigosViewModel) {
    val scope = rememberCoroutineScope()

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

    AmigosContent(
        usuarios = viewModel.listaAmigosMostrada,
        navHost = navHost,
        onSearch = { viewModel.buscarAmigos(it) },
        onInvitar = { viewModel.invitarAmigo(it) },
        onUnirse = { nombre ->
            viewModel.unirseAPartida(
                nombre,
                onSuccess = { navHost.goTo(Destinos.JUGAR_CREAR) },
                onError = {
                    scope.launch {
                        snackHost.showSnackbar(
                            message = "No te has podido unir al lobby de $nombre",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            )
        },
        onBorrar = { viewModel.borrarAmigo(it) },
        onAnadir = { viewModel.anadirAmigo(it) }
    )
}

@Composable
fun AmigosContent(
    usuarios: List<Usuario>,
    navHost: SENavHostController,
    onSearch: (String) -> Unit,
    onInvitar: (String) -> Unit,
    onUnirse: (String) -> Unit,
    onBorrar: (String) -> Unit,
    onAnadir: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cabecera, buscar amigos y volver a la pantalla anterior
        CabeceraAmigos(navHost, onSearch = onSearch, onAdd = {})

        ListaAmigos(
            usuarios = usuarios,
            onInvitar = onInvitar,
            onUnirse = onUnirse,
            onBorrar = onBorrar,
            onAnadir = onAnadir
        )
    }
}

@Preview(widthDp = 800, heightDp = 480)
@Composable
fun AmigosScreenPreview() {
    val mockUsuarios = listOf(
        Usuario("Ivan", estaOnline = true, haInvitado = false, esAmigo = true, estadoTexto = "En el lobby"),
        Usuario("Paco", estaOnline = false, haInvitado = true, esAmigo = true, estadoTexto = "Desconectado"),
        Usuario("Maria", estaOnline = true, haInvitado = false, esAmigo = false, estadoTexto = "Jugando")
    )

    MaterialTheme {
        AmigosContent(
            usuarios = mockUsuarios,
            navHost = rememberSEAppState(),
            onSearch = {},
            onInvitar = {},
            onUnirse = {},
            onBorrar = {},
            onAnadir = {}
        )
    }
}
