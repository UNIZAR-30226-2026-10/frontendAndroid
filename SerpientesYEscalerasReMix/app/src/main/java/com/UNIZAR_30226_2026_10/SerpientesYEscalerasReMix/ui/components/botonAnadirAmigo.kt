package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_primary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_secondary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_text

@Composable
fun AnadirAmigoBoton(onAnadir: (String) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    var nombreAmigo by remember { mutableStateOf("") }

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Surface(
                color = color_secondary,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(2.dp, color_primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Añadir Amigo",
                        style = SETextTypes.grande,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Campo de texto con icono de búsqueda y placeholder
                    OutlinedTextField(
                        value = nombreAmigo,
                        onValueChange = { nombreAmigo = it },
                        placeholder = {
                            Text(
                                "Nombre del usuario...",
                                style = SETextTypes.plano,
                                color = Color.Gray
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = color_text
                            )
                        },
                        singleLine = true,
                        textStyle = SETextTypes.plano,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = color_primary,
                            unfocusedBorderColor = color_text.copy(alpha = 0.5f),
                            cursorColor = color_primary
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Botón Cancelar
                        TextButton(onClick = {
                            showDialog = false
                            nombreAmigo = ""
                        }) {
                            Text("Cancelar", style = SETextTypes.plano, color = color_text)
                        }

                        // Botón Añadir
                        Button(
                            onClick = {
                                if (nombreAmigo.isNotBlank()) {
                                    onAnadir(nombreAmigo)
                                    showDialog = false
                                    nombreAmigo = ""
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = color_primary),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Añadir", style = SETextTypes.plano, color = color_text)
                        }
                    }
                }
            }
        }
    }

    Surface(
        color = color_secondary,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(2.dp, color_primary),
        onClick = { showDialog = true },
        modifier = Modifier
            .width(44.dp)
            .height(44.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Añadir Amigo",
                tint = color_text,
            )

            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = color_text,
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.BottomEnd)
            )
        }
    }
}

@Preview
@Composable
fun AnadirAmigoBotonPreview() {
    AnadirAmigoBoton(onAnadir = {})
}
