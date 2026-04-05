package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R

@Preview
@Composable
fun DadoBoton() {
    Image(
        painter = painterResource(id = R.drawable.icono_dado),
        contentDescription = "icono jugador",
        modifier = Modifier
            .size(120.dp)
            .shadow(
                elevation = 10.dp,
                shape = CircleShape,
                clip = false
            )
            .clip(CircleShape)
    )
}