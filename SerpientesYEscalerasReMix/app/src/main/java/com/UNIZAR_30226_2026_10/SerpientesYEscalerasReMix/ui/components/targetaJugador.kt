package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_sf

@Composable
fun JugadorItem(icono: Int, nombreIcono: String, nombreJugador: String) {
    var distanciaIcono = 11.dp
    var offsetIcono = -22.dp

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(top = distanciaIcono)
    ) {
        Surface(
            shape = RoundedCornerShape(18.dp),
            color = color_sf,
            modifier = Modifier
                .sizeIn(maxWidth = 200.dp, maxHeight = 300.dp)
                .width(70.dp)
        ) {
            Box(
                modifier = Modifier.padding(16.dp), // Espaciado interno
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(nombreJugador)
                }
            }
        }

        Box(
            modifier = Modifier.offset(y = offsetIcono)
        ) {
            JugadorIcon(icono, nombreIcono)
        }
    }
}

@Composable
fun JugadorIcon(icono: Int, nombreIcono: String) {
    Box(contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .blur(1.dp)
                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
        )

        Image(
            painter = painterResource(id = icono),
            contentDescription = nombreIcono,
            modifier = Modifier
                .size(25.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = CircleShape,
                    clip = false
                )
                .clip(CircleShape)
                .border(1.dp, color_bg, CircleShape)
        )
    }
}
