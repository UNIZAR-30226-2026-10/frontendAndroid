package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.unit.dp
import androidx.compose.ui.input.pointer.pointerInput
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Carta
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Calidad
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_cardComun
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_cardEpica
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_cardLegendaria
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_cardRara
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fondoTienda
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_sf
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_text
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.foundation.gestures.detectTapGestures

@Composable
fun CartaImagen(
    carta: Carta,
    modifier: Modifier = Modifier,
    mostrarNombreFallback: Boolean = true
) {
    val imagenRes = carta.imagen ?: 0

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color_fondoTienda, RoundedCornerShape(8.dp))
            .border(2.dp, colorCartaPorCalidad(carta.calidad), RoundedCornerShape(8.dp))
    ) {
        if (imagenRes != 0) {
            Image(
                painter = painterResource(id = imagenRes),
                contentDescription = "imagen carta",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else if (mostrarNombreFallback) {
            Text(
                text = carta.nombre,
                style = SETextTypes.pequeno,
                color = color_text,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(6.dp)
            )
        }
    }
}

@Composable
fun CartaDetalleDialog(
    carta: Carta,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = true)
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = color_bg,
            border = androidx.compose.foundation.BorderStroke(2.dp, color_sf),
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar detalle",
                        tint = color_text
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 32.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CartaImagen(
                        carta = carta,
                        modifier = Modifier
                            .width(90.dp)
                            .aspectRatio(0.7f)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = carta.nombre,
                            style = SETextTypes.mediano,
                            color = color_text
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = carta.descripcion,
                            style = SETextTypes.plano,
                            color = color_text
                        )
                    }
                }
            }
        }
    }
}

private fun colorCartaPorCalidad(calidad: Calidad) = when (calidad) {
    Calidad.Comun -> color_cardComun
    Calidad.Rara -> color_cardRara
    Calidad.Epica -> color_cardEpica
    Calidad.Legendaria -> color_cardLegendaria
}

fun Modifier.longPressAfter(
    delayMillis: Long,
    onLongPress: () -> Unit
): Modifier = pointerInput(onLongPress) {
    detectTapGestures(
        onPress = {
            coroutineScope {
                val job = launch {
                    delay(delayMillis)
                    onLongPress()
                }
                tryAwaitRelease()
                job.cancel()
            }
        }
    )
}
