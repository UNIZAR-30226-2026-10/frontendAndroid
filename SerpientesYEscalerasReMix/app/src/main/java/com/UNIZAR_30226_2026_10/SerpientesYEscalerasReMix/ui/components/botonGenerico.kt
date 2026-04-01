package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_sf
import java.util.Locale

@Composable
fun BotonGenerico(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colorPrincipal: Color = color_sf,
    habilitado: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = habilitado,
        modifier = modifier
            .height(50.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorPrincipal,
            contentColor = Color.Gray
        ),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, color_sf),
    ) {
        Text(
            text = texto.uppercase(Locale.ROOT),
            style = SETextTypes.plano, //TODO
            color = Color.White
        )
    }
}