package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_primary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_secondary

// Boton para abandonar el lobby
@Composable
fun ChatBoton(onClick: () -> Unit) {
    Surface(
        color = color_secondary,
        shape = RoundedCornerShape(16.dp),
        onClick = { onClick() },
        modifier = Modifier
            .height(40.dp),
        border = BorderStroke(2.dp, color_primary)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(5.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.icono_chat),
                contentDescription = "chat",
                modifier = Modifier
                    .size(35.dp)
                    .graphicsLayer(scaleX = -1f)
            )

            Text("Chat", modifier = Modifier.padding(horizontal = 4.dp))
        }
    }
}


@Preview()
@Composable
fun PartidaScreenPreview() {
    ChatBoton({})
}