package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Jugar_Continuar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.CrearBoton
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.ListaPartidas
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController

@Composable
fun JugarContinuarScreen(SEState: SENavHostController, viewModel: JugarContinuarViewModel) {
    var opcionSeleccionada = "Continuar"

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            CrearBoton(SEState, "der")
            Spacer(modifier = Modifier.width(16.dp))
        }

        ListaPartidas(SEState, viewModel.listaPartidas, onTarjeta = { id -> viewModel.continuar(id) })
    }
}
