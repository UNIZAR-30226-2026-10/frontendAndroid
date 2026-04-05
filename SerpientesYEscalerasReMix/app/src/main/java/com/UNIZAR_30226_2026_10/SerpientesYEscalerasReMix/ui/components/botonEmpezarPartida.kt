package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_selected
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_unselected

@Composable
fun EmpezarPartidaBoton(
    esLider: Boolean,
    estaListo: Boolean,
    todosListos: Boolean, // Nueva condición para el líder
    onEmpezar: () -> Unit,
    onCambiarListo: (Boolean) -> Unit
) {
    // Estado interno para el pop-up de error
    var showErrorDialog by remember { mutableStateOf(false) }

    // Diálogo de error (solo para el líder)
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            confirmButton = {
                TextButton(onClick = { showErrorDialog = false }) {
                    Text("Entendido")
                }
            },
            title = { Text("No se puede empezar") },
            text = { Text("Todos los jugadores deben estar en estado 'Listo' para poder comenzar la partida.") }
        )
    }

    Surface(
        modifier = Modifier
            .width(180.dp)
            .height(40.dp),
        color = if (!esLider && estaListo) color_unselected else color_selected,
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 8.dp,
        onClick = {
            if (esLider) {
                if (todosListos) {
                    onEmpezar()
                } else {
                    showErrorDialog = true
                }
            } else {
                onCambiarListo(estaListo)
            }
        }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text(
                text = when {
                    esLider -> "Empezar Partida"
                    estaListo -> "No Preparado"
                    else -> "Preparado"
                },
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = SETextTypes.grande
            )
        }
    }
}
