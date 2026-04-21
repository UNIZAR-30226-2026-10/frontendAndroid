package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.MsgChat
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_primary

@Composable
fun DialogoChat(
    mensajes: List<MsgChat>,
    onClose: () -> Unit,
    onSend: (String) -> Unit
) {
    var textState by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = { onClose() },
        properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .fillMaxHeight(0.8f),
            color = color_bg,
            border = BorderStroke(2.dp, color_primary),
            shape = RoundedCornerShape(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                // Cabecera
                Text(
                    text = "CHAT DE PARTIDA",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Área de mensajes
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    reverseLayout = false
                ) {
                    items(mensajes) { msg ->
                        Row(modifier = Modifier.padding(vertical = 2.dp)) {
                            Text(
                                text = if (msg.isSystem) "[Sistema]: " else "${msg.sender}: ",
                                color = if (msg.isSystem) color_primary
                                else color_fg,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = msg.message,
                                color = color_fg,
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                // Input de texto
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .background(color_bg.copy(alpha = 0.5f))
                        .border(0.5.dp, color_primary)
                        .padding(8.dp)
                ) {
                    BasicTextField(
                        value = textState,
                        onValueChange = { textState = it },
                        singleLine = true,
                        // Usamos tus tipos de texto y colores
                        textStyle = MaterialTheme.typography.bodyMedium.copy(color = androidx.compose.ui.graphics.Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .padding(horizontal = 8.dp),
                        decorationBox = { innerTextField ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                // Icono de enviar
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Send,
                                    contentDescription = "Enviar",
                                    tint = color_primary,
                                    modifier = Modifier.size(20.dp)
                                )

                                Spacer(Modifier.width(8.dp))

                                // Box con el subrayado dinámico
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .drawBehind {
                                            val strokeWidthPx = 1.dp.toPx()
                                            val y = size.height - 4.dp.toPx()
                                            drawLine(
                                                color = color_primary.copy(alpha = 0.5f),
                                                start = Offset(x = 0f, y = y),
                                                end = Offset(x = size.width, y = y),
                                                strokeWidth = strokeWidthPx
                                            )
                                        }
                                ) {
                                    if (textState.isEmpty()) {
                                        Text(
                                            text = "Escribe un mensaje...",
                                            color = androidx.compose.ui.graphics.Color.Gray,
                                            fontSize = 14.sp
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                        keyboardActions = KeyboardActions(
                            onSend = {
                                onSend(textState)
                                textState = ""
                            }
                        )
                    )
                }
            }
        }
    }
}