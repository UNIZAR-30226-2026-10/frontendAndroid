package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CaseFacade
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.Destinos
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Amigo
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_primary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_secondary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_text
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


@Composable
fun CabeceraAmigos(
    SEState: SENavHostController,
    cF: CaseFacade,
    onBusquedaFinalizada: (List<Amigo>) -> Unit
) {
    // Para poder ejecutar corutines
    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Vovler a Crear Partida
        Surface(
            onClick = { SEState.goTo(Destinos.JUGAR_CREAR) },
            color = color_secondary,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(2.dp, color_primary)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Volver",
                    tint = color_text,
                    modifier = Modifier
                        .size(18.dp)
                        .graphicsLayer(scaleX = -1f) // Espejo horizontal
                )
                Spacer(Modifier.width(8.dp))
                Text(text = "Crear partida")
            }
        }

        Spacer(Modifier.width(80.dp))

        // Titulo
        Text(
            text = "Amigos",
            style = SETextTypes.grande,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

        // Búsqueda de amigos
        var searchText by remember { mutableStateOf("") }

        Surface(
            color = color_secondary,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(2.dp, color_primary),
            modifier = Modifier
                .height(40.dp)
                .width(300.dp)
        ) {
            BasicTextField(
                value = searchText,
                onValueChange = { searchText = it },
                singleLine = true, // Subrayado
                textStyle = SETextTypes.plano.copy(color = color_text),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp), // Margen
                decorationBox = { innerTextField ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search, // Lupa
                            contentDescription = "Buscar",
                            tint = color_text,
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(Modifier.width(8.dp)) // Espacio entre icono y texto

                        // Box en la que se dibuja el texto
                        Box(
                            modifier = Modifier
                                .weight(1f) // Ocupar el resto del espacio
                                .drawBehind {
                                    val strokeWidthPx = 1.dp.toPx()
                                    val y = size.height - 2.dp.toPx()
                                    drawLine(
                                        color = color_text.copy(alpha = 0.5f),
                                        start = Offset(x = 0f, y = y),
                                        end = Offset(x = size.width - 20.dp.toPx(), y = y),
                                        strokeWidth = strokeWidthPx
                                    )
                                }
                        ) {
                            // Si el texto está vacío, mostrar el placeholder
                            if (searchText.isEmpty()) {
                                Text(
                                    text = "Buscar...",
                                    style = SETextTypes.plano,
                                    color = color_text.copy(alpha = 0.5f)
                                )
                            }
                            // Objeto en el que se dibuja el texto
                            innerTextField()
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search), // Acción a esperar del teclado del usuario
                keyboardActions = KeyboardActions( // Llamada a esperar cuando llega la acción anterior
                    onSearch = {
                        scope.launch {
                            val listaAmigosDef = searchAmigoAction(cF, scope, searchText)
                            val listaAmgiosBusqueda = listaAmigosDef.await()
                            onBusquedaFinalizada(listaAmgiosBusqueda)
                        }
                    }
                ),
            )
        }
    }
}

fun searchAmigoAction(cF: CaseFacade, scope: CoroutineScope, searchText: String): Deferred<List<Amigo>> {
    if (searchText != "") {
        return scope.async {
            cF.amigosCase.buscarAmigos(searchText)
        }
    } else {
        return CompletableDeferred(emptyList())
    }
}
