package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fondoTienda
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_sf
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_text

@Composable
fun ConfirmacionJuego(
    titulo: String,
    mensaje: String,
    textoCancelar: String,
    textoAceptar: String,
    onCancelar: () -> Unit,
    onAceptar: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color_bg.copy(alpha = 0.6f)),
        color = color_bg.copy(alpha = 0.6f)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.78f)
                    .clip(RoundedCornerShape(18.dp))
                    .border(2.dp, color_sf, RoundedCornerShape(18.dp))
                    .background(color_fondoTienda)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = titulo,
                    style = SETextTypes.grande,
                    color = color_text
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = mensaje,
                    style = SETextTypes.plano,
                    color = color_text
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = textoCancelar,
                        style = SETextTypes.mediano,
                        color = color_text,
                        modifier = Modifier
                            .background(color_sf, RoundedCornerShape(8.dp))
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                            .clickable { onCancelar() }
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = textoAceptar,
                        style = SETextTypes.mediano,
                        color = color_text,
                        modifier = Modifier
                            .background(color_sf, RoundedCornerShape(8.dp))
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                            .clickable { onAceptar() }
                    )
                }
            }
        }
    }
}
