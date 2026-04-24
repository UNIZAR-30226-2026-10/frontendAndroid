package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.fakes.fakeFichasSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.fakes.fakeTableroSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.FichaSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Movimiento
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.TableroSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.TipoCasilla
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SerpientesYEscalerasReMixTheme
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_primary

@Composable
fun Tablero(
    tableroState: TableroSnapshot,
    fichasState: List<FichaSnapshot>,

    seleccionFicha: Boolean,
    seleccionarFicha: (Int) -> Unit,

    seleccionCasilla: Boolean,
    casillasAElegir: List<Movimiento>, // Usado también con seleccionCasillaCarta
    seleccionarCasilla: (Int, Boolean) -> Unit,

    seleccionFichaCarta: Boolean,
    seleccionarFichaCarta: (Int) -> Unit,

    seleccionCasillaCarta: Boolean,
    seleccionarCasillaCarta: (Int) -> Unit

) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        // BoxWithConstraints para medir el tamaño real en píxeles
        BoxWithConstraints(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()
        ) {

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

                                // Codigo correspondiente a una casilla en particular

                                val numCasilla = (fil - 1) * 10 + col
                                val casilla = tableroState.casillas[numCasilla - 1]

                                // En caso de que sea el momento de elegir una casilla a la que moverse
                                val haySeleccion = seleccionCasilla || seleccionCasillaCarta

                                val esCasillaElegible =
                                    haySeleccion && casillasAElegir.any { it.casillaId == numCasilla }

                                val debeOscurecer = haySeleccion && !esCasillaElegible

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .then( // Añadir acción de elegir solo cuando esCasillaElegible
                                            if (esCasillaElegible) {
                                                Modifier
                                                    .clickable {
                                                        if (seleccionCasilla) {
                                                            seleccionarCasilla(numCasilla, casilla.siguientes.size > 1) // casilla.siguientes.size > 1 = esBifurcacion
                                                        } else { // seleccionCasillaCarta
                                                            seleccionarCasillaCarta(numCasilla)
                                                        }
                                                    }
                                            } else Modifier
                                        ),
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
                                    val fichasCasilla =
                                        fichasState.filter { it.casilla == numCasilla }

                                    FichasStackSnapshot(
                                        fichas = fichasCasilla,
                                        seleccionFicha = seleccionFicha,
                                        seleccionarFicha = seleccionarFicha,
                                        seleccionFichaCarta = seleccionFichaCarta,
                                        seleccionarFichaCarta = seleccionarFichaCarta
                                    )

                                    // En caso de que tenga que oscurecerse
                                    if (debeOscurecer) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(Color.Black.copy(alpha = 0.6f))
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Capa de serpientes y escaleras
            ColocarSerpientesEscaleras(tableroState, casillaPx)
        }
    }
}

@Composable
fun ColocarSerpientesEscaleras(tableroState: TableroSnapshot, casillaPx: Float) {
    val bmpSerpienteCabeza = ImageBitmap.imageResource(id = R.drawable.serpiente_base_cabeza)
    val bmpSerpienteCuerpo = ImageBitmap.imageResource(id = R.drawable.serpiente_base_cuerpo)
    val bmpSerpienteCola = ImageBitmap.imageResource(id = R.drawable.serpiente_base_cola)
    val bmpEscalera = ImageBitmap.imageResource(id = R.drawable.escalera)

    Canvas(
        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxWidth()
    ) {
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

                // Ajustar tamaño de las serpientes y escaleras
                val grosorSerpiente = casillaPx * 0.3f
                val largoCabeza = grosorSerpiente * (834f / 288f)
                val anchoCabeza = casillaPx * 0.35f
                val largoCola = grosorSerpiente * 3f
                val debeReflejar = anguloDeg in 90.0..<180.0

                val grosorEscalera = casillaPx * 0.7f
                val largoEscalera = distancia
                val anchoEscalera =
                    grosorEscalera * (bmpEscalera.width.toFloat() / bmpEscalera.height.toFloat())

                rotate(anguloDeg, pivot = start) {
                    if (casilla.tipo == TipoCasilla.Serpiente) {
                        // Para que la serpiente este siempre boca arriba

                        val distanciaRestante =
                            (distancia - largoCabeza - largoCola).coerceAtLeast(0f)
                        val ratioCuerpo = 281f / 254f
                        val anchoCuerpoBase = grosorSerpiente * ratioCuerpo
                        val numCuerpos =
                            (distanciaRestante / anchoCuerpoBase).toInt().coerceAtLeast(1)
                        val anchoCuerpoReal = distanciaRestante / numCuerpos

                        // Cabeza
                        withTransform({
                            if (debeReflejar) scale(
                                1f,
                                -1f,
                                pivot = Offset(start.x + anchoCuerpoReal / 2, start.y)
                            )
                        }) {
                            drawImage(
                                image = bmpSerpienteCabeza,
                                dstOffset = IntOffset(
                                    start.x.toInt() + 2,
                                    (start.y - grosorSerpiente / 2).toInt() - 4
                                ),
                                dstSize = IntSize(largoCabeza.toInt(), anchoCabeza.toInt())
                            )
                        }

                        // Segmentos cuerpo
                        for (i in 0 until numCuerpos) {
                            val xPos = start.x + largoCabeza + i * anchoCuerpoReal
                            withTransform({
                                if (debeReflejar) scale(
                                    1f,
                                    -1f,
                                    pivot = Offset(xPos + anchoCuerpoReal / 2, start.y)
                                )
                            }) {
                                drawImage(
                                    image = bmpSerpienteCuerpo,
                                    dstOffset = IntOffset(
                                        (start.x + largoCabeza + i * anchoCuerpoReal).toInt(),
                                        (start.y - grosorSerpiente / 2).toInt()
                                    ),
                                    dstSize = IntSize(
                                        (anchoCuerpoReal + 1).toInt(),
                                        grosorSerpiente.toInt()
                                    )
                                )
                            }
                        }

                        // Cola
                        val xPosCola = start.x + distancia - largoCola
                        withTransform({
                            if (debeReflejar) scale(
                                1f,
                                -1f,
                                pivot = Offset(xPosCola + anchoCuerpoReal / 2, start.y)
                            )
                        }) {
                            drawImage(
                                image = bmpSerpienteCola,
                                dstOffset = IntOffset(
                                    (start.x + distancia - largoCola).toInt(),
                                    (start.y - grosorSerpiente / 2).toInt()
                                ),
                                dstSize = IntSize(largoCola.toInt(), grosorSerpiente.toInt())
                            )
                        }
                    } else {
                        val numTramos = (largoEscalera / anchoEscalera).toInt().coerceAtLeast(1)
                        val anchoTramoReal = largoEscalera / numTramos

                        for (i in 0 until numTramos) {
                            drawImage(
                                image = bmpEscalera,
                                dstOffset = IntOffset(
                                    (start.x + i * anchoTramoReal).toInt(),
                                    (start.y - grosorEscalera / 2).toInt()
                                ),
                                dstSize = IntSize(
                                    (anchoTramoReal + 1).toInt(),
                                    grosorEscalera.toInt()
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FichasStackSnapshot(
    fichas: List<FichaSnapshot>,
    seleccionFicha: Boolean = false,
    seleccionarFicha: (Int) -> Unit = {},

    seleccionFichaCarta: Boolean = false,
    seleccionarFichaCarta: (Int) -> Unit = {}
) {

    val fichasProcesadas: List<Pair<Int, FichaSnapshot>> = if (fichas.size > 2) {
        // Si es la primera casilla (fichas.size > 2), mostrar solo 1 por jugador con cuenta de estas
        fichas.groupBy { it.idJugador }.map { it.value.size to it.value.first() }
    } else {
        // Sino poner todas las fichas (bloqueos y solo una)
        fichas.map { 1 to it }
    }

    // Tamaño de la ficha respecto a la casilla (%)
    val fractionCasilla =
        if (fichasProcesadas.size == 1) 0.8f
        else 0.45f

    FlowColumn(
        modifier = Modifier.fillMaxSize(),
        maxItemsInEachColumn = 2,
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Center
    ) {
        fichasProcesadas.forEach { (cuenta, ficha) ->

            // Lógica de validación para mostrar recuadro y permitir click
            val esFichaSeleccionable =
                (seleccionFicha || seleccionFichaCarta) && ficha.esUsuario && !ficha.meta


            Box(
                modifier = Modifier
                    .fillMaxWidth(fractionCasilla) // Asegura el 2x2
                    .aspectRatio(1f)
                    .then( // Añadir borde
                        if (esFichaSeleccionable) {
                            Modifier
                                .border(2.dp, color_primary, RoundedCornerShape(4.dp))
                                .clickable {
                                    if (seleccionFicha) {
                                        seleccionarFicha(ficha.id)
                                    } else { // seleccionFichaCarta
                                        seleccionarFichaCarta(ficha.id)
                                    }
                                }
                        } else Modifier
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (ficha.idImg != null) {

                    Box(modifier = Modifier.fillMaxSize(0.9f)) {
                        // Imagen de la ficha
                        Image(
                            painter = painterResource(id = ficha.idImg),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )

                        // Cuenta de fichas por jugador en la pantlla inicial y bloqueos
                        if (cuenta > 1) {
                            Box(
                                modifier = Modifier
                                    .offset(y = (-5).dp, x = 2.dp)
                                    .align(Alignment.TopEnd)
                                    .fillMaxSize(0.5f) // El badge ocupa el 50% de la ficha
                                    .background(color_bg, CircleShape)
                                    .border(1.dp, color_fg, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = cuenta.toString(),
                                    fontSize = 6.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
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

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp,orientation=landscape")
@Composable
fun TableroPreview() {

    SerpientesYEscalerasReMixTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = color_bg
        ) {
            val movimientos = listOf<Movimiento>(
                Movimiento(
                    fichaId = 1,
                    casillaId = 2,
                    esBifurcacion = false,
                    pasosRestantes = 0
                ),

                Movimiento(
                    fichaId = 1,
                    casillaId = 8,
                    esBifurcacion = false,
                    pasosRestantes = 0
                )
            )

            Tablero(
                fakeTableroSnapshot,
                fakeFichasSnapshot,
                false,
                { ficha -> },
                false,
                movimientos,
                { casilla, esBifurcacion ->  },
                false,
                { ficha -> },
                false,
                { casilla -> }

            )
        }
    }
}
