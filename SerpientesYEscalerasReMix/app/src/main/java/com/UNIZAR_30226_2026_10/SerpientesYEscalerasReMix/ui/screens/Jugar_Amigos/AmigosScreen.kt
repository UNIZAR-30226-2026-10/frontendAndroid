package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Jugar_Amigos

import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Usuario
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.CabeceraAmigos
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.ListaAmigos
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.fijarOrientacion
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.Destinos
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.rememberSEAppState

@Composable
fun AmigosScreen(
    navController: SENavHostController,
    viewModel: AmigosViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    // Mantener orientación horizontal para esta pantalla
    fijarOrientacion(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

    LaunchedEffect(Unit) {
        viewModel.iniciarPolling()
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.detenerPolling()
        }
    }

    AmigosContent(
        usuarios = uiState.listaAmigosMostrada,
        navHost = navController,
        onSearch = { viewModel.buscarAmigos(it) },
        onInvitar = { viewModel.invitarAmigo(it) },
        onUnirse = { nombre ->
            viewModel.unirseAPartida(nombre) {
                navController.goTo(Destinos.JUGAR_CREAR)
            }
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
        CabeceraAmigos(navHost, onSearch = onSearch, onAdd = { onAnadir(it) })

        ListaAmigos(
            usuarios = usuarios,
            onInvitar = onInvitar,
            onUnirse = onUnirse,
            onBorrar = onBorrar,
            onAnadir = onAnadir
        )
    }
}

@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,orientation=landscape")
@Composable
fun AmigosPreview() {
    val mockUsuarios = listOf(
        Usuario("Juan", "En línea", true, true),
        Usuario("Maria", "Te ha invitado", true, true, true),
        Usuario("Pedro", "Desconectado", false, true)
    )
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
