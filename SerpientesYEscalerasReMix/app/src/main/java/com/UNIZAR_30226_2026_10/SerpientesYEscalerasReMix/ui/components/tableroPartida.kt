package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.offset
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SerpientesYEscalerasReMixTheme
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_negative
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_positive
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_primary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_selected
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_unselected


// TODO refactorizar estas clases/objects

data class Ficha(
    val id: String,
    var posicion: Int,
    val colorHex: String,
    val equipo: String
)

data class ConfirmacionEscalera(
    val fichaId: String,
    val casillaBase: Int,
    val casillaSalto: Int
)

enum class TipoCasilla {
    Normal, Escalera, Serpiente, Bifurcacion, Meta, Curva
}

data class Casilla(
    val esCurva: Boolean = false,
    val rotacion: Int = 0,
    val tipo: TipoCasilla = TipoCasilla.Normal,
    val siguientes: List<Int> = emptyList(),
    val saltoA: Int? = null
)

object TableroData {
    val sparseCasillasArray: Array<Casilla?> = arrayOfNulls<Casilla>(100).apply {

        // --- Bloque 1: Casillas 1 a 44 ---
        for (numeroCasilla in 1..44) {
            val indice = numeroCasilla - 1
            val fila = (numeroCasilla - 1) / 10
            val enBordeDerecho = numeroCasilla % 10 == 0
            val enBordeIzquierdo = numeroCasilla % 10 == 1
            val filaPar = fila % 2 == 0
            val ultimaFilaCamino = (45 - 1) / 10

            val inicioFila = if (filaPar) fila * 10 + 1 else fila * 10 + 10
            val finFila = if (filaPar) fila * 10 + 10 else fila * 10 + 1

            val esCurvaSubida = numeroCasilla == finFila && fila < ultimaFilaCamino
            val esCurvaBajada = numeroCasilla == inicioFila && fila > 0
            val esCurvaBorde = esCurvaSubida || esCurvaBajada

            var rotacion = 90
            if (esCurvaSubida) {
                rotacion = if (enBordeDerecho) 180 else 270
            } else if (esCurvaBajada) {
                rotacion = if (enBordeDerecho) 90 else 0
            }

            var siguiente: Int? = null
            if (numeroCasilla < 45) {
                siguiente = if (filaPar) {
                    if (enBordeDerecho) numeroCasilla + 10 else numeroCasilla + 1
                } else {
                    if (enBordeIzquierdo) numeroCasilla + 10 else numeroCasilla - 1
                }
            }

            this[indice] = Casilla(
                esCurva = esCurvaBorde,
                rotacion = rotacion,
                tipo = TipoCasilla.Normal,
                siguientes = if (siguiente != null) listOf(siguiente) else emptyList()
            )
        }

        // --- Casillas manuales 45-46 ---
        this[44] = Casilla(esCurva = false, rotacion = 90, tipo = TipoCasilla.Normal, siguientes = listOf(46))
        this[45] = Casilla(esCurva = true, rotacion = 180, tipo = TipoCasilla.Normal, siguientes = listOf(56))

        // --- Bloque 2: Casillas 51 a 90 ---
        for (i in 51..90) {
            val modulo = i % 10
            val fila = (i - 1) / 10
            val esFilaInferior = (fila % 2) == 1
            val filaPar = fila % 2 == 0

            // Usamos if independientes (SIN else) para que el comportamiento
            // de sobreescritura sea el mismo que en tu React
            if (modulo == 5) {
                this[i - 1] = Casilla(esCurva = true, rotacion = if (esFilaInferior) 0 else 270, tipo = TipoCasilla.Normal, siguientes = if (esFilaInferior) listOf(i + 1) else listOf(i + 10))
            }
            if (modulo in 2..4) {
                this[i - 1] = Casilla(esCurva = false, rotacion = 90, tipo = TipoCasilla.Normal, siguientes = if (filaPar) listOf(i + 1) else listOf(i - 1))
            }
            if (modulo in 6..9) {
                this[i - 1] = Casilla(esCurva = false, rotacion = 90, tipo = TipoCasilla.Normal, siguientes = if (filaPar) listOf(i - 1) else listOf(i + 1))
            }
            if (modulo == 0) {
                this[i - 1] = Casilla(esCurva = true, rotacion = if (esFilaInferior) 180 else 90, tipo = TipoCasilla.Curva, siguientes = if (esFilaInferior) listOf(i + 10) else listOf(i - 1))
            }
            if (modulo == 1) {
                this[i - 1] = Casilla(esCurva = true, rotacion = if (esFilaInferior) 270 else 0, tipo = TipoCasilla.Curva, siguientes = if (esFilaInferior) listOf(i + 10) else listOf(i + 1))
            }
            if (modulo == 4) { // ¡Esto ahora sí sobreescribirá correctamente el 2..4!
                this[i - 1] = Casilla(esCurva = true, rotacion = if (esFilaInferior) 90 else 180, tipo = TipoCasilla.Curva, siguientes = if (esFilaInferior) listOf(i - 1) else listOf(i + 10))
            }
        }

        // --- Casillas manuales y Bifurcaciones ---
        this[53] = Casilla(esCurva = false, rotacion = 90, tipo = TipoCasilla.Normal, siguientes = listOf(54)) // Fix duplicado en TS
        this[54] = Casilla(esCurva = false, rotacion = 90, tipo = TipoCasilla.Normal, siguientes = listOf(55))
        this[55] = Casilla(esCurva = false, rotacion = 270, tipo = TipoCasilla.Bifurcacion, siguientes = listOf(57, 55))
        this[93] = Casilla(esCurva = true, rotacion = 0, tipo = TipoCasilla.Curva, siguientes = listOf(95))

        for (i in 96..99) {
            this[i - 1] = Casilla(esCurva = false, rotacion = 90, tipo = TipoCasilla.Normal, siguientes = listOf(i + 1))
        }

        this[94] = Casilla(esCurva = false, rotacion = 270, tipo = TipoCasilla.Bifurcacion, siguientes = listOf(96))
        this[99] = Casilla(esCurva = false, rotacion = 270, tipo = TipoCasilla.Meta, siguientes = emptyList())

        // --- Serpientes (Sobre-escritura) ---
        applyObstaculo(16, TipoCasilla.Serpiente, 9)
        applyObstaculo(53, TipoCasilla.Serpiente, 43)
        applyObstaculo(67, TipoCasilla.Serpiente, 19)
        applyObstaculo(82, TipoCasilla.Serpiente, 56)
        applyObstaculo(96, TipoCasilla.Serpiente, 25)

        // --- Escaleras (Sobre-escritura) ---
        applyObstaculo(6, TipoCasilla.Escalera, 26, extraSiguientes = listOf(8))
        applyObstaculo(14, TipoCasilla.Escalera, 31)
        applyObstaculo(61, TipoCasilla.Escalera, 73, curva = true, rot = 0, extraSiguientes = listOf(61))
        applyObstaculo(36, TipoCasilla.Escalera, 98)
        applyObstaculo(64, TipoCasilla.Escalera, 84, curva = true, rot = 180, extraSiguientes = listOf(75))
    }

    // Función auxiliar para no repetir código al poner serpientes/escaleras
    private fun Array<Casilla?>.applyObstaculo(
        num: Int,
        tipo: TipoCasilla,
        salto: Int,
        curva: Boolean = false,
        rot: Int = 90,
        extraSiguientes: List<Int>? = null
    ) {
        val original = this[num - 1]
        this[num - 1] = Casilla(
            esCurva = curva,
            rotacion = rot,
            tipo = tipo,
            siguientes = extraSiguientes ?: original?.siguientes ?: emptyList(),
            saltoA = salto
        )
    }
}

fun getInitialFichas(): List<Ficha> = listOf(
    Ficha("Ficha 1", 1, "#F87171", "miEquipo"),
    Ficha("Ficha 2", 1, "#EF4444", "miEquipo"),
    Ficha("Jugador 2 - Ficha 1", 1, "#60A5FA", "equipoAzul"),
    Ficha("Jugador 3 - Ficha 1", 1, "#4ADE80", "equipoVerde"),
    Ficha("Jugador 4 - Ficha 1", 1, "#FDE047", "equipoAmarillo")
)

@Composable
fun Tablero(
    equipoActual: String,
    valorDadoExterno: Int?,
    onAvanzarTurno: () -> Unit,
    onTirarDadoManual: (Int?) -> Unit
) {
    // --- ESTADO ---
    val casillasData = TableroData.sparseCasillasArray
    var misFichas by remember { mutableStateOf(getInitialFichas()) }
    var fichaSeleccionadaId by remember { mutableStateOf<String?>(null) }
    var confirmacionEscalera by remember { mutableStateOf<ConfirmacionEscalera?>(null) }

    val movimientosPermitidos = remember(valorDadoExterno, equipoActual, misFichas) {
        if (valorDadoExterno == null) emptyMap<String, List<Int>>()
        else {
            misFichas.associate { ficha ->
                if (ficha.equipo != equipoActual) ficha.id to emptyList()
                else {
                    val destinos = casillasData[ficha.posicion - 1]?.siguientes ?: emptyList()
                    // Nota: Aquí podrías implementar una lógica recursiva si el dado es > 1
                    ficha.id to destinos
                }
            }
        }
    }

    val destinosIluminados = movimientosPermitidos[fichaSeleccionadaId] ?: emptyList()

    val bmpSerpiente = ImageBitmap.imageResource(id = R.drawable.serpiente)
    val bmpEscalera = ImageBitmap.imageResource(id = R.drawable.escalera)

    Box(modifier = Modifier.fillMaxSize().padding(8.dp), contentAlignment = Alignment.Center) {
        Card(
            modifier = Modifier.aspectRatio(1f).fillMaxWidth().border(2.dp, color_unselected, RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(containerColor = color_bg),
            shape = RoundedCornerShape(16.dp)
        ) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val boardSize = constraints.maxWidth.toFloat()
                val cellSize = boardSize / 10

                // CAPA DE FONDO (Cuadrícula e Imágenes de Casillas)
                Column(modifier = Modifier.fillMaxSize()) {
                    for (fila in 9 downTo 0) {
                        Row(modifier = Modifier.weight(1f)) {
                            for (col in 1..10) {
                                val numCasilla = fila * 10 + col
                                val casillaInfo = casillasData.getOrNull(numCasilla - 1)
                                val esDestino = destinosIluminados.contains(numCasilla)
                                val fichasEnCasilla = misFichas.filter { it.posicion == numCasilla }

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .clickable(enabled = esDestino) {
                                            // Lógica de movimiento aquí
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    // Determinar Imagen
                                    val imageRes = when {
                                        casillaInfo == null -> R.drawable.casilla_vacia
                                        casillaInfo.tipo == TipoCasilla.Meta -> R.drawable.casilla_meta
                                        casillaInfo.tipo == TipoCasilla.Bifurcacion -> R.drawable.casilla_bifurcacion
                                        casillaInfo.esCurva || casillaInfo.tipo == TipoCasilla.Curva -> R.drawable.casilla_curva
                                        else -> R.drawable.casilla_vertical
                                    }

                                    Image(
                                        painter = painterResource(id = imageRes),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .rotate(casillaInfo?.rotacion?.toFloat() ?: 0f)
                                    )

                                    // Overlay de destino
                                    if (esDestino) {
                                        Box(Modifier.fillMaxSize().background(color_positive.copy(alpha = 0.3f)))
                                    }

                                    Text(
                                        text = numCasilla.toString(),
                                        style = SETextTypes.plano,
                                        modifier = Modifier.align(Alignment.TopStart).padding(2.dp)
                                    )

                                    FichasStack(fichasEnCasilla, fichaSeleccionadaId) { id ->
                                        if (movimientosPermitidos[id]?.isNotEmpty() == true) fichaSeleccionadaId = id
                                    }
                                }
                            }
                        }
                    }
                }

                // CAPA DE OBSTÁCULOS (Serpientes y Escaleras que cruzan el tablero)
                Canvas(modifier = Modifier.fillMaxSize()) {
                    casillasData.forEachIndexed { index, casilla ->
                        if (casilla != null && casilla.saltoA != null) {
                            val inicio = getCenterOfCasilla(index + 1, cellSize)
                            val fin = getCenterOfCasilla(casilla.saltoA, cellSize)

                            val isEscalera = casilla.tipo == TipoCasilla.Escalera
                            val imgBitmap = if (isEscalera) bmpEscalera else bmpSerpiente

                            // Matemáticas equivalentes al React
                            val dx = fin.x - inicio.x
                            val dy = fin.y - inicio.y
                            val distance = kotlin.math.hypot(dx.toDouble(), dy.toDouble()).toFloat()
                            val angleDegrees = kotlin.math.atan2(dy.toDouble(), dx.toDouble()).toFloat() * (180f / kotlin.math.PI.toFloat())

                            // Proporciones del CSS: Escalera 7%, Serpiente 18% del tamaño de tablero
                            val imgHeight = boardSize * if (isEscalera) 0.07f else 0.18f

                            withTransform({
                                translate(left = inicio.x, top = inicio.y)
                                rotate(degrees = angleDegrees, pivot = Offset.Zero)
                                // Movemos hacia arriba la mitad del alto para que rote desde el centro vertical (transformOrigin: 0% 50%)
                                translate(left = 0f, top = -imgHeight / 2f)
                            }) {
                                drawImage(
                                    image = imgBitmap,
                                    dstSize = IntSize(distance.toInt(), imgHeight.toInt())
                                )
                            }
                        }
                    }
                }
            }
        }

        // Feedback de Movimiento (Overlay)
        if (fichaSeleccionadaId != null) {
            Surface(
                modifier = Modifier.align(Alignment.TopCenter).offset(y = 60.dp),
                color = color_bg,
                shape = CircleShape,
                border = BorderStroke(1.dp, color_positive.copy(alpha = 0.5f))
            ) {
                Text(
                    text = "Moviendo $fichaSeleccionadaId. ¡Elige casilla!",
                    style = SETextTypes.SEPStyle,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }

        // Diálogo de Confirmación de Escalera
        if (confirmacionEscalera != null) {
            DialogConfirmacionEscalera(
                saltoA = confirmacionEscalera!!.casillaSalto,
                onConfirm = { /* Ejecutar movimiento final */ },
                onCancel = { /* Quedarse en base */ }
            )
        }
    }
}

fun getCenterOfCasilla(numCasilla: Int, cellSize: Float): Offset {
    val fila = (numCasilla - 1) / 10  // 0 a 9
    val col = (numCasilla - 1) % 10   // 0 a 9

    // Invertimos la fila para el Canvas (y=0 es arriba)
    val y = (9 - fila) * cellSize + (cellSize / 2)
    val x = col * cellSize + (cellSize / 2)

    return Offset(x, y)
}

@Composable
fun FichasStack(
    fichas: List<Ficha>,
    seleccionadaId: String?,
    onFichaClick: (String) -> Unit
) {
    val grupos = fichas.groupBy { it.equipo }

    // FlowRow permite que si hay muchas fichas (como en la casilla 1),
    // se distribuyan en varias filas automáticamente.
    FlowRow(
        modifier = Modifier.padding(2.dp),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Center,
        maxItemsInEachRow = 3
    ) {
        grupos.forEach { (_, fichasEquipo) ->
            val fichaAMostrar = fichasEquipo.find { it.id == seleccionadaId } ?: fichasEquipo.first()
            val esSeleccionada = fichaAMostrar.id == seleccionadaId

            Box(
                modifier = Modifier
                    .padding(1.dp)
                    .size(if (esSeleccionada) 20.dp else 14.dp)
                    .clip(CircleShape)
                    .background(Color(android.graphics.Color.parseColor(fichaAMostrar.colorHex)))
                    .border(
                        width = if (esSeleccionada) 2.dp else 1.dp,
                        color = if (esSeleccionada) color_selected else color_fg,
                        shape = CircleShape
                    )
                    .clickable { onFichaClick(fichaAMostrar.id) },
                contentAlignment = Alignment.Center
            ) {
                // Si hay más de una ficha del mismo equipo en la casilla, -> número
                if (fichasEquipo.size > 1) {
                    Text(
                        text = fichasEquipo.size.toString(),
                        style = SETextTypes.plano.copy(
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
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
                    Button(onClick = onConfirm, colors = ButtonDefaults.buttonColors(containerColor = color_positive)) {
                        Text("SÍ", style = SETextTypes.plano)
                    }
                    Button(onClick = onCancel, colors = ButtonDefaults.buttonColors(containerColor = color_negative)) {
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
    // Usamos el tema de tu aplicación para que coja tipografías y estilos
    SerpientesYEscalerasReMixTheme {
        // Ponemos un Surface de fondo con tu color_bg para que el contraste sea real
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = color_bg
        ) {
            Tablero(
                equipoActual = "miEquipo",
                valorDadoExterno = 5, // Simulamos que el jugador ha sacado un 5
                onAvanzarTurno = {
                    println("Preview: Avanzar turno")
                },
                onTirarDadoManual = { valor ->
                    println("Preview: Dado seteado a $valor")
                }
            )
        }
    }
}
