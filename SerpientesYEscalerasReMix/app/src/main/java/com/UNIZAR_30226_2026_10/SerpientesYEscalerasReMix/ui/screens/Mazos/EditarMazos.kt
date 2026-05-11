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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Carta
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fondoTienda
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_offline
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_selectedText
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_sf
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_text
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.CartaDetalleDialog
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.CartaImagen
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.longPressAfter
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.Destinos
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Mazo

@Composable
fun EditarMazosScreen(navController: SENavHostController, viewModel: MazosViewModel) {

    val state by viewModel.editarMazoUiState.collectAsState()

    when (val s = state) {
        is EditarMazoUiState.Loading -> {
            // Mostrar pantalla de carga
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Cargando mazo...")
            }
        }

        is EditarMazoUiState.Success -> {
            // Mostrar los mazos
            EditarMazoContent(
                mazoAntiguo = viewModel.mazoSeleccionado,
                mazoAEditar = viewModel.mazoSeleccionado,
                cartasDisponibles = viewModel.cartasDisponibles,
                onGuardarCambios = { mazoAntiguo, mazoAEditar ->
                    // guardar cambios en el servidor
                    viewModel.guardarCambios(mazoAntiguo, mazoAEditar)

                },
                onSalir = {
                    navController.navController.popBackStack()
                },
                onActualizarNombre = { nuevoNombre ->
                    viewModel.actualizarNombreMazoSeleccionado(nuevoNombre)
                },
                onAnadirCarta = { carta ->
                    viewModel.anadirCartaAMazoSeleccionado(carta)
                },
                onEliminarCarta = { indice ->
                    viewModel.eliminarCartaAMazoSeleccionado(indice)
                }
            )
        }

        is EditarMazoUiState.Error -> {
            // Mostrar mensaje de error
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error al cargar el mazo: ${s.message}")
            }
        }
    }

}

@Composable
fun EditarMazoContent(
    mazoAntiguo: Mazo,
    mazoAEditar: Mazo,
    cartasDisponibles: List<Carta>,
    onGuardarCambios: (Mazo, Mazo) -> Unit,
    onSalir: () -> Unit,
    onActualizarNombre: (String) -> Unit,
    onAnadirCarta: (Carta) -> Unit,
    onEliminarCarta: (Int) -> Unit

){

    val mazoCompleto = mazoAEditar.cartas.size >= 10
    var cartaDetalle by remember { mutableStateOf<Carta?>(null) }
    var mostrarSalida by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color_bg)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Boton guardar cambios
            IconButton(
                onClick = {
                    // guardar cambios en el servidor
                        onGuardarCambios(mazoAntiguo, mazoAEditar)
                },
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Guardar cambios",
                    tint = color_text
                )
            }

            // Icono cierre
            IconButton(
                onClick = { mostrarSalida = true },
                modifier = Modifier
                    .offset(x = (-4).dp, y = 4.dp)
                    .size(36.dp)
                    .rotate(45f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.plus_simbol),
                    contentDescription = "Salir",
                    tint = color_text,
                    modifier = Modifier.size(30.dp)
                )
            }


        }

        Spacer(modifier = Modifier.height(12.dp))

        if (mostrarSalida) {
            AlertDialog(
                onDismissRequest = { mostrarSalida = false },
                title = {
                    Text(
                        text = "Salir sin guardar",
                        style = SETextTypes.mediano,
                        color = color_text
                    )
                },
                text = {
                    Text(
                        text = "No se guardaran los cambios al mazo.",
                        style = SETextTypes.plano,
                        color = color_text
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            mostrarSalida = false
                            onSalir()
                        }
                    ) {
                        Text("Salir", color = color_text)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { mostrarSalida = false }) {
                        Text("Cancelar", color = color_text)
                    }
                },
                containerColor = color_bg
            )
        }

        Text(
            text = "Nombre del mazo",
            style = SETextTypes.seleccionable,
            color = color_text
        )

        Spacer(modifier = Modifier.height(8.dp))

        var nombreMazo by remember(mazoAEditar.nombre) { mutableStateOf(mazoAEditar.nombre) }

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
                        onActualizarNombre(it)
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
                val carta = mazoAEditar.cartas.getOrNull(index)
                CartaMazoSlot(
                    carta = carta,
                    onRemove = {
                        if (carta != null) {
                            onEliminarCarta(index)
                        }
                    },
                    onLongPress = { cartaDetalle = carta }
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

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            cartasDisponibles.chunked(5).forEach { fila ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    fila.forEach { carta ->
                        CartaDisponibleItem(
                            carta = carta,
                            habilitado = !mazoCompleto,
                            onAdd = { onAnadirCarta(carta) },
                            onLongPress = { cartaDetalle = carta }
                        )
                    }
                    repeat(5 - fila.size) {
                        Spacer(modifier = Modifier.width(80.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        cartaDetalle?.let { carta ->
            CartaDetalleDialog(
                carta = carta,
                onDismiss = { cartaDetalle = null }
            )
        }
    }
}

@Composable
private fun CartaMazoSlot(
    carta: Carta?,
    onRemove: () -> Unit,
    onLongPress: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(80.dp)
            .aspectRatio(0.7f)
            .clip(RoundedCornerShape(8.dp))
            .background(color_fondoTienda)
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
            CartaImagen(
                carta = carta,
                modifier = Modifier
                    .fillMaxSize()
                    .longPressAfter(1000L, onLongPress)
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
private fun CartaDisponibleItem(
    carta: Carta,
    habilitado: Boolean,
    onAdd: () -> Unit,
    onLongPress: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CartaImagen(
            carta = carta,
            modifier = Modifier
                .width(80.dp)
                .aspectRatio(0.7f)
                .longPressAfter(1000L, onLongPress)
        )

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
