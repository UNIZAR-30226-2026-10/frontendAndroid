package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Jugar_Amigos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.CabeceraAmigos
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.ListaAmigos
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController

@Composable
fun AmigosScreen(SEState: SENavHostController, viewModel: AmigosViewModel) {

    // Activar polling al entrar en la pantalla
    LaunchedEffect(Unit) {
        viewModel.iniciarPollingAmigos()
    }

    // Desactivar polling cuando la pantalla no sea visible
    DisposableEffect(Unit) {
        onDispose {
            viewModel.detenerPollingAmigos()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cabecera, buscar amigos y volver a la pantalla anterior
        CabeceraAmigos(SEState, onSearch = { searchText -> viewModel.buscarAmigos(searchText) })

        ListaAmigos(viewModel, SEState, viewModel.listaAmigosMostrada)
    }
}
