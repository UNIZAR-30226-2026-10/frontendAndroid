package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_primary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_secondary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_text

@Composable
fun ElegirTableroBoton(tablero: Int) {
    Surface(
        modifier = Modifier
            .size(120.dp),
        color = color_secondary,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, color_primary) // Borde amarillo grueso
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título superior
            Text(
                text = "Tablero",
                modifier = Modifier.padding(vertical = 4.dp),
                style = SETextTypes.grande
            )

            // Contenedor de la imagen e icono
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.background(color = color_bg)
            ) {
                // Imagen del tablero (reemplaza con tu recurso)
                Image(
                    painter = painterResource(id = tablero),
                    contentDescription = "Tablero de juego",
                    modifier = Modifier
                        .height(80.dp)
                        .width(80.dp)
                        .alpha(0.8f),
                    contentScale = ContentScale.Crop,
                )

                // Icono de edición (el lápiz blanco)
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar",
                    tint = color_text,
                    modifier = Modifier
                        .size(60.dp)
                )
            }
        }
    }
}
