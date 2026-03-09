package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.Destinos
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes

@Composable
fun eleccionCrearContinuar(opcion: String, SEState: SENavHostController) {
    val crearStyle = if (opcion == "Crear") SETextTypes.seleccionado
    else SETextTypes.seleccionable

    val continuarStyle = if (opcion != "Crear") SETextTypes.seleccionado
    else SETextTypes.seleccionable

    Row(
        modifier = Modifier.padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Crear Partida", style = crearStyle,
            modifier = Modifier.clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) {
                if (opcion != "Crear") {
                    SEState.goTo(Destinos.JUGAR_CREAR)
                }
            })

        Spacer(modifier = Modifier.width(60.dp))

        Text(
            text = "Continuar Partida",
            style = continuarStyle,
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                if (opcion == "Crear") {
                    Log.d("debug", "El botón ha sido pulsado")
                    SEState.goTo(Destinos.JUGAR_CONTINUAR)
                }
            }
        )
    }

}