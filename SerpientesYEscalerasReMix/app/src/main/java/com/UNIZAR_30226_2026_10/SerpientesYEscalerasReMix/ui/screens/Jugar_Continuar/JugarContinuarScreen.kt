package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Jugar_Continuar

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.ListaPartidas
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.eleccionCrearContinuar
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController

@Composable
fun JugarContinuarScreen(SEState: SENavHostController, viewModel: JugarContinuarViewModel) {
    var opcionSeleccionada = "Continuar"

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        eleccionCrearContinuar(opcionSeleccionada, SEState)

        ListaPartidas(SEState, viewModel.listaPartidas, onTarjeta = { id -> viewModel.continuar(id) })
    }
}
