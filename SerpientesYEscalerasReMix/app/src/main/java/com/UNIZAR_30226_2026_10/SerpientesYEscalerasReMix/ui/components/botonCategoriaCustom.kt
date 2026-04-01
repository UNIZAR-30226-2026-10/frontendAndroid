package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_sf
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_selected
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fondoTienda
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight

@Composable
fun BotonCategoriaCustom(
    titulo: String,
    estaSeleccionado: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(25.dp)
            .background(if (estaSeleccionado) color_selected else color_fondoTienda)
            .border(2.dp, color_sf) // Borde amarillo para todos
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = titulo,
            style = SETextTypes.plano, //TODO
            color = color_sf, // Texto en amarillo
            fontWeight = FontWeight.Bold
        )
    }
}