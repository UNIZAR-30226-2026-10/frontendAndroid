package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_primary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_secondary

@Preview
@Composable
fun DadoBoton() {
    Surface(
        modifier = Modifier
            .padding(8.dp)
            .width(120.dp), // Ajustamos el ancho al tamaño del dado
        shape = RoundedCornerShape(24.dp),
        color = color_secondary,
        border = BorderStroke(2.dp, color_primary),
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                // Eliminamos el padding vertical excesivo para que cierre justo al acabar el dado
                .padding(top = 8.dp, bottom = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Lanzar Dado",
                textAlign = TextAlign.Center,
                style = SETextTypes.grande,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )

            Text(
                text = "* terminará tu turno",
                textAlign = TextAlign.Center,
                style = SETextTypes.pequeno,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.icono_dado),
                contentDescription = "icono jugador",
                modifier = Modifier
                    .size(100.dp) // Reducimos un poco el dado para que respire dentro de los 120dp
                    .shadow(
                        elevation = 10.dp,
                        shape = CircleShape,
                        clip = false
                    )
                    .clip(CircleShape)
            )
        }
    }
}