package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.fakes.fakeTableroSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.FichaSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.TableroSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.TipoCasilla
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SerpientesYEscalerasReMixTheme
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_negative
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_positive
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_primary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_unselected

data class Ficha(
    val id: String,
    var posicion: Int,
    val colorHex: String,
    val equipo: String
)

@Composable
fun Tablero(
    tableroState: TableroSnapshot
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp), contentAlignment = Alignment.Center) {

        // BoxWithConstraints para medir el tamaño real en píxeles
        BoxWithConstraints(modifier = Modifier.aspectRatio(1f).fillMaxWidth()) {

            val tableroSizePx = constraints.maxWidth.toFloat()
            val casillaPx = tableroSizePx / 10

            Card(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxWidth()
                    .border(2.dp, color_primary, RoundedCornerShape(6.dp)),
                colors = CardDefaults.cardColors(containerColor = color_bg)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    for (fil in 10 downTo 1) {

                        // Serpientes / Escaleras
                        Row(modifier = Modifier.weight(1f)) {
                            for (col in 1..10) {
                                val numCasilla = (fil - 1) * 10 + col
                                val casilla = tableroState.casillas[numCasilla - 1]

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    // Selección de imagen
                                    val casillaImg = when {
                                        casilla.tipo == TipoCasilla.Vacio -> R.drawable.casilla_vacia
                                        casilla.tipo == TipoCasilla.Meta -> R.drawable.casilla_meta
                                        casilla.tipo == TipoCasilla.Bifurcacion -> R.drawable.casilla_bifurcacion
                                        casilla.esCurva -> R.drawable.casilla_curva
                                        casilla.tipo == TipoCasilla.Normal && !casilla.esCurva -> R.drawable.casilla_vertical
                                        else -> R.drawable.casilla_vertical // Por defecto
                                    }

                                    Image(
                                        painter = painterResource(id = casillaImg),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .rotate(casilla.rotacion.toFloat()) // Valor del snapshot
                                    )

                                    Text(
                                        text = numCasilla.toString(),
                                        style = SETextTypes.plano.copy(fontSize = 10.sp),
                                        modifier = Modifier
                                            .align(Alignment.TopStart)
                                            .padding(2.dp)
                                    )

                                    // Fichas
                                    FichasStackSnapshot(casilla.fichasEnCasilla)
                                }
                            }
                        }
                    }
                }
            }



            // Convertir el drawable a ImageBitmap
            val bmpSerpiente = ImageBitmap.imageResource(id = R.drawable.serpiente)
            val bmpEscalera = ImageBitmap.imageResource(id = R.drawable.escalera)

            Canvas(modifier = Modifier.aspectRatio(1f).fillMaxWidth()) {
                tableroState.casillas.forEachIndexed { index, casilla ->
                    val numOrigen = index + 1
                    if (casilla.saltoA != null) {
                        val start = getCenterOfCasilla(numOrigen, casillaPx)
                        val end = getCenterOfCasilla(casilla.saltoA, casillaPx)

                        val dx = end.x - start.x
                        val dy = end.y - start.y
                        val distancia = kotlin.math.sqrt(dx * dx + dy * dy)
                        val anguloRad = kotlin.math.atan2(dy, dx)
                        val anguloDeg = (anguloRad * (180.0 / kotlin.math.PI)).toFloat()

                        val bitmap = if (casilla.tipo == TipoCasilla.Serpiente) bmpSerpiente else bmpEscalera
                        val grosor = if (casilla.tipo == TipoCasilla.Serpiente) {
                            60.dp.toPx() // Grosor constante para serpientes
                        } else {
                            20.dp.toPx() // Grosor constante para escaleras (más anchas)
                        }

                        rotate(anguloDeg, pivot = start) {
                            drawImage(
                                image = bitmap,
                                dstOffset = IntOffset(start.x.toInt(), (start.y - grosor / 2).toInt()),
                                dstSize = IntSize(distancia.toInt(), grosor.toInt())
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FichasStackSnapshot(fichas: List<FichaSnapshot>) { // TODO añadir onClick
    FlowRow(
        modifier = Modifier.padding(2.dp),
        maxItemsInEachRow = 3
    ) {
        fichas.forEach { ficha ->
            Box(
                modifier = Modifier
                    .size(14.dp)
                    .clip(CircleShape)
                    .background(color_primary) // TODO Color dependiente de lógica de equipo
                    .border(1.dp, Color.White, CircleShape)
            )
        }
    }
}

fun getCenterOfCasilla(numCasilla: Int, casillaPx: Float): Offset {
    val fila = (numCasilla - 1) / 10
    val col = (numCasilla - 1) % 10
    val y = (9 - fila) * casillaPx + (casillaPx / 2)
    val x = col * casillaPx + (casillaPx / 2)
    return Offset(x, y)
}

@Composable
fun DialogConfirmacionEscalera(saltoA: Int, onConfirm: () -> Unit, onCancel: () -> Unit) {
    Dialog(onDismissRequest = {}) {
        Card(
            colors = CardDefaults.cardColors(containerColor = color_unselected),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(2.dp, color_primary)
        ) {
            Column(Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("🪜", fontSize = 40.sp)
                Text(
                    "¡HAS CAÍDO EN UNA ESCALERA!",
                    style = SETextTypes.grande.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center
                )
                Text(
                    "¿Quieres subir a la casilla $saltoA?",
                    style = SETextTypes.sombreado,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = onConfirm,
                        colors = ButtonDefaults.buttonColors(containerColor = color_positive)
                    ) {
                        Text("SÍ", style = SETextTypes.plano)
                    }
                    Button(
                        onClick = onCancel,
                        colors = ButtonDefaults.buttonColors(containerColor = color_negative)
                    ) {
                        Text("NO", style = SETextTypes.plano)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp,orientation=landscape")
@Composable
fun TableroPreview() {

    SerpientesYEscalerasReMixTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = color_bg
        ) {
            Tablero(tableroState = fakeTableroSnapshot)
        }
    }
}
