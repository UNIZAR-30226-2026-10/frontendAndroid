package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.buscarMiniaturaTableroR
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_primary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_secondary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_text

private data class TableroOpcion(val nombre: String, val resId: Int)

@Composable
fun ElegirTableroBoton(tableroResId: Int, nombreTableros: List<String>, onClick: (String) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    val opciones = nombreTableros.map { nombre ->
        TableroOpcion(nombre, buscarMiniaturaTableroR(nombre))
    }

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Surface(
                modifier = Modifier
                    .width(300.dp)
                    .height(400.dp),
                color = color_secondary,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(2.dp, color_primary)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Elegir Tablero",
                        style = SETextTypes.grande,
                        color = color_text,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        items(opciones) { opcion ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(color_bg)
                                    .clickable {
                                        onClick(opcion.nombre)
                                        showDialog = false
                                    }
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = opcion.resId),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(RoundedCornerShape(4.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = opcion.nombre,
                                    style = SETextTypes.plano,
                                    color = color_text
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    Surface(
        modifier = Modifier
            .size(120.dp),
        color = color_secondary,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, color_primary), // Borde amarillo grueso
        onClick = { showDialog = true }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título superior
            Text(
                text = "Tablero",
                modifier = Modifier.padding(vertical = 4.dp),
                style = SETextTypes.grande
            )

            // Contenedor de la imagen e icono
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.background(color = color_bg)
            ) {
                // Imagen del tablero
                Image(
                    painter = painterResource(id = tableroResId),
                    contentDescription = "Tablero de juego",
                    modifier = Modifier
                        .height(80.dp)
                        .width(80.dp)
                        .alpha(0.8f),
                    contentScale = ContentScale.Crop,
                )

                // Icono de edición (el lápiz blanco)
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar",
                    tint = color_text,
                    modifier = Modifier
                        .size(60.dp)
                )
            }
        }
    }
}
