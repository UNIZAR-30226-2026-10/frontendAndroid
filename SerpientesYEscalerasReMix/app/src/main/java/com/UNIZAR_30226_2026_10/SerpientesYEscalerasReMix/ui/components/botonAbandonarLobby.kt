package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.Destinos
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_negative
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_text

@Composable
fun AbandonarLobbyBoton(SEState: SENavHostController, onClick: () -> Unit) {
    // Estado para controlar si el diálogo se muestra
    var showDialog by remember { mutableStateOf(false) }

    // Diálogo de confirmación
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false }, // Se cierra si pulsas fuera
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    SEState.goTo(Destinos.JUGAR_CREAR)
                    onClick()
                }) {
                    Text("Abandonar", color = color_negative)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            },
            title = { Text("Abandonar Lobby") },
            text = { Text("¿Seguro que quieres abandonar el lobby?") }
        )
    }

    // Boton para abandonar el lobby
    Surface(
        color = color_negative,
        shape = RoundedCornerShape(16.dp),
        onClick = { showDialog = true },
        modifier = Modifier
            .width(40.dp)
            .height(40.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(5.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = "Abandonar",
                tint = color_text,
                modifier = Modifier
                    .size(35.dp)
                    .graphicsLayer(scaleX = -1f)
            )
        }
    }
}

