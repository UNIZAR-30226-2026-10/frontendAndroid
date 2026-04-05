package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Carta
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_primary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_selected
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_unselected

@Composable
fun MazoVisual(onSelectCarta: (Carta) -> Unit) {
    val mano = listOf(
        Carta(1, "Moises", imagen =  R.drawable.carta_moises, efecto = "Te saltas el bloqueo"),
        Carta(2, "Moises", imagen = R.drawable.carta_moises, efecto = "Te saltas el bloqueo"),
        Carta(3, "Moises", imagen = R.drawable.carta_moises, efecto = "Te saltas el bloqueo"),
        null
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(4.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(mano.size) { idx ->
            val carta = mano[idx]
            if (carta == null) {
                Box(
                    modifier = Modifier
                        .aspectRatio(0.66f)
                        .background(color_bg, RoundedCornerShape(8.dp))
                        .drawBehind {
                            drawRoundRect(
                                color = color_fg.copy(alpha = 0.4f),
                                //style = Stroke(width = 2.dp.toPx(), pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f))
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text("SIN CARTA", style = SETextTypes.sombreado, fontSize = 10.sp)
                }
            } else {
                Card(
                    modifier = Modifier
                        .aspectRatio(0.66f)
                        .clickable { onSelectCarta(carta) },
                    colors = CardDefaults.cardColors(containerColor = color_bg),
                    border = BorderStroke(2.dp, color_primary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Título
                        Text(
                            text = carta.nombre.uppercase(),
                            style = SETextTypes.plano,
                            modifier = Modifier.drawBehind {
                                val strokeWidth = 2.dp.toPx()
                                val y = size.height + 4.dp.toPx()
                                drawLine(
                                    color_selected,
                                    Offset(0f, y),
                                    Offset(size.width, y),
                                    strokeWidth
                                )
                            }
                        )

                        // Imagen de la carta
                        Image(
                            painter = painterResource(id = carta.imagen),
                            contentDescription = "imagen carta",
                            modifier = Modifier.width(100.dp)
                        )

                        // Efecto
                        Surface(
                            color = color_unselected.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, color_selected.copy(alpha = 0.5f))
                        ) {
                            Text(
                                text = "\"${carta.efecto}\"",
                                style = SETextTypes.plano.copy(fontStyle = FontStyle.Italic),
                                modifier = Modifier.padding(12.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MazoPrev() {
    MazoVisual({ _ -> })
}
