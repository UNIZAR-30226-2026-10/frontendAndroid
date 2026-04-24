package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_text


@Composable
fun DialogoPuntuacionDado(
    puntuacion: Int,
    visible: Boolean,
    modifier: Modifier
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(10.dp))
                .background(color_bg.copy(alpha = 0.9f))
                .padding(vertical = 4.dp, horizontal = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = puntuacion.toString(),
                style = TextStyle(
                    color = color_text,
                    fontSize = 100.sp
                ),
                textAlign = TextAlign.Center,
            )
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun DialogoPuntuacionDado() {
    DialogoIndicacionPartida(
        mensaje = "Moviendo Ficha 1. Dado: 3. ¡Elige una casilla verde!"
    )
}