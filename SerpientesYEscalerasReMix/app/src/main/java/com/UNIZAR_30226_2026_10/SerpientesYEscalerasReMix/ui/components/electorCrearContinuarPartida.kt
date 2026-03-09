package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

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

    val modCrear = if (opcion != "Crear") Modifier.clickable() { /* Vacio */ }
    else Modifier.clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { SEState.goTo("${Destinos.JUGAR_BASE}?modo=Crear") }

    val modContinuar = if (opcion == "Crear") Modifier.clickable() { /* Vacio */ }
    else Modifier.clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { SEState.goTo("${Destinos.JUGAR_BASE}?modo=Continuar") }


    Row(
        modifier = Modifier.padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Crear Partida", style = crearStyle, modifier = modCrear)

        Spacer(modifier = Modifier.width(60.dp))

        Text("Continuar Partida", style = continuarStyle, modifier = modContinuar)
    }

}