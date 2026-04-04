package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.*


@Composable
fun Opciones(
    onClose: () -> Unit = {},
    email: String = "Serpiente@gmail.com",
    onChangePassword: () -> Unit = {},
    onVolumeChange: (Float) -> Unit = {},
){
    var volumen by remember{ mutableStateOf(0.5f) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color_bg),
        contentAlignment = Alignment.Center
    ){
        Surface(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(0.9f)
                .height(300.dp),
            border = BorderStroke(2.dp, color_primary),
            shape = RoundedCornerShape(8.dp),
            color = color_secondary
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Opciones",
                        style = SETextTypes.seleccionable.copy(fontSize = 25.sp),
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                    IconButton(
                        onClick = onClose,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar",
                            tint = color_text,
                            modifier = Modifier
                                .size(28.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Correo Electrónico",
                    style = SETextTypes.seleccionable.copy(fontSize = 18.sp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color_bg, shape = RoundedCornerShape(4.dp))
                            .padding(4.dp)
                    ) {
                        Text(
                            text = email,
                            style = SETextTypes.grande.copy(fontSize = 10.sp),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Contraseña",
                    style = SETextTypes.seleccionable.copy(fontSize = 18.sp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .background(color_bg, shape = RoundedCornerShape(4.dp))
                            .padding(4.dp)
                    ) {
                        Text(
                            text = "******",
                            style = SETextTypes.grande.copy(fontSize = 10.sp),
                        )
                    }
                    Surface(
                        onClick = onChangePassword,
                        color = Color.Gray, // Un gris neutro para que destaque
                        shape = RoundedCornerShape(4.dp),
                        border = BorderStroke(1.dp, color_text), // Borde para que se vea "físico"
                        modifier = Modifier.height(20.dp) // Altura fija para que sea consistente
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        ) {
                            Text(
                                text = "Cambiar",
                                style = SETextTypes.seleccionable.copy(
                                    fontSize = 14.sp,
                                    color = Color.Black // Texto oscuro sobre fondo gris
                                )
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Volumen",
                    style = SETextTypes.seleccionable.copy(fontSize = 18.sp)
                )
                Row() {
                    Slider(
                        value = volumen,
                        onValueChange = { newVal ->
                            volumen = newVal
                            onVolumeChange(newVal)
                        },
                        modifier = Modifier
                            .weight(1f),
                        colors = SliderDefaults.colors(
                            thumbColor = color_bg,
                            activeTrackColor = color_primary,
                            inactiveTrackColor = color_text
                        )
                    )
                    Text(
                        text = "🔊",
                        fontSize = 26.sp,
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
            }
        }
    }
}
