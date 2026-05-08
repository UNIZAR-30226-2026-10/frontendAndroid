package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Mazos

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Carta
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Calidad
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_cardComun
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_cardEpica
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_cardLegendaria
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_cardRara
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fondoTienda
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_offline
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_selectedText
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_sf
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_text
import androidx.compose.foundation.text.BasicTextField

@Composable
fun EditarMazosScreen(navController: SENavHostController, viewModel: MazosViewModel) {

    /*LaunchedEffect(viewModel.mazoSeleccionado) {
        viewModel.seleccionarMazoPorNumero()
    }*/

    val mazo = viewModel.mazoSeleccionado
    val cartasDisponibles = viewModel.cartasDisponibles
    val mazoCompleto = mazo.cartas.size >= 10

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color_bg)
            .padding(16.dp)
    ) {
        Text(
            text = "Editar mazo",
            style = SETextTypes.titulo,
            color = color_text,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Nombre del mazo",
            style = SETextTypes.seleccionable,
            color = color_text
        )

        Spacer(modifier = Modifier.height(8.dp))

        var nombreMazo by remember(mazo.nombre) { mutableStateOf(mazo.nombre) }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            color = color_fondoTienda,
            shape = RoundedCornerShape(6.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, color_sf)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = nombreMazo,
                    onValueChange = {
                        nombreMazo = it
                        viewModel.actualizarNombreMazo(it)
                    },
                    textStyle = SETextTypes.plano.copy(fontSize = 16.sp, color = color_text),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    cursorBrush = SolidColor(color_sf)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Cartas del mazo",
            style = SETextTypes.seleccionable,
            color = color_text
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items((0 until 10).toList()) { index ->
                val carta = mazo.cartas.getOrNull(index)
                CartaMazoSlot(
                    carta = carta,
                    onRemove = {
                        if (carta != null) {
                            viewModel.eliminarCarta(index)
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Cartas disponibles",
            style = SETextTypes.seleccionable,
            color = color_text
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(cartasDisponibles) { carta ->
                CartaDisponibleItem(
                    carta = carta,
                    habilitado = !mazoCompleto,
                    onAdd = { viewModel.anadirCarta(carta) }
                )
            }
        }
    }
}

@Composable
private fun CartaMazoSlot(carta: Carta?, onRemove: () -> Unit) {
    Box(
        modifier = Modifier
            .width(80.dp)
            .aspectRatio(0.7f)
            .clip(RoundedCornerShape(8.dp))
            .background(carta?.let { colorCarta(it) } ?: color_fondoTienda)
            .border(1.dp, color_sf.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
    ) {
        if (carta == null) {
            Text(
                text = "Vacio",
                style = SETextTypes.sombreado,
                color = color_selectedText,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            Text(
                text = carta.nombre,
                style = SETextTypes.pequeno,
                color = color_text,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(6.dp)
            )
            IconButton(
                onClick = onRemove,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar carta",
                    tint = color_offline,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
private fun CartaDisponibleItem(carta: Carta, habilitado: Boolean, onAdd: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .width(80.dp)
                .aspectRatio(0.7f)
                .clip(RoundedCornerShape(8.dp))
                .background(colorCarta(carta))
                .border(1.dp, color_sf.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = carta.nombre,
                style = SETextTypes.pequeno,
                color = color_text,
                modifier = Modifier.padding(6.dp)
            )
        }

        IconButton(
            onClick = onAdd,
            enabled = habilitado,
            modifier = Modifier.size(28.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = "Anadir carta",
                tint = if (habilitado) color_sf else color_selectedText,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

private fun colorCarta(carta: Carta): Color {
    return when (carta.calidad) {
        Calidad.Comun -> color_cardComun
        Calidad.Rara -> color_cardRara
        Calidad.Epica -> color_cardEpica
        Calidad.Legendaria -> color_cardLegendaria
    }
}
