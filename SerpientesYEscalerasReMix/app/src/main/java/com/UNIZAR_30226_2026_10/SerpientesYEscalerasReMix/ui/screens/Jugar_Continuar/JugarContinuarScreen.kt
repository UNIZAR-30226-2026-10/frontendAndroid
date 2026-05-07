package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Jugar_Continuar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.CrearBoton
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.ListaPartidas
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes

@Composable
fun JugarContinuarScreen(navHost: SENavHostController, viewModel: JugarContinuarViewModel) {
    // Observamos el estado del UI
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(text = "Continuar Partidas", style = SETextTypes.titulo)
            Spacer(modifier = Modifier.width(175.dp))
            CrearBoton(navHost, "der")
            Spacer(modifier = Modifier.width(16.dp))
        }

        // Pasamos la lista desde el uiState
        ListaPartidas(
            navHost = navHost,
            partidas = uiState.listaPartidas,
            onTarjeta = { id -> viewModel.continuar(id) }
        )
    }
}
