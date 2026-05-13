package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Logros

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.*
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.LogroUsuario

@Composable
fun LogrosScreen(SEState: SENavHostController, viewModel: LogrosViewModel) {
    val listaDeLogros = viewModel.logros
    val estaCargando = viewModel.cargando
    val error = viewModel.errorMessage
    val errorReclamar = viewModel.errorReclamar

    if (errorReclamar != null) {
        AlertDialog(
            onDismissRequest = { viewModel.limpiarErrorReclamar() },
            title = { Text(text = "Fallo al reclamar", style = SETextTypes.titulo, color = color_primary) },
            text = { Text(text = errorReclamar, style = SETextTypes.plano, color = color_text) },
            confirmButton = {
                Button(
                    onClick = { viewModel.limpiarErrorReclamar() },
                    colors = ButtonDefaults.buttonColors(containerColor = color_primary)
                ) {
                    Text("Aceptar", color = color_secondary)
                }
            },
            containerColor = color_bg
        )
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Logros",
            style = SETextTypes.titulo,
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            textAlign = TextAlign.Center
        )

        if (estaCargando) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = color_primary)
            }
        } else if (error != null) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = error, color = color_text, modifier = Modifier.padding(16.dp))
                Button(onClick = { viewModel.cargarLogros() }) { Text("Reintentar") }
            }
        } else {
            ListaLogros(listaDeLogros) { id -> viewModel.reclamarLogro(id) }
        }
    }
}

@Composable
fun ListaLogros(logros: List<LogroUsuario>, onReclamar: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(logros) { logro ->
            TarjetaLogro(logro, onReclamar)
        }
    }
}

@Composable
fun TarjetaLogro(logro: LogroUsuario, onReclamar: (String) -> Unit) {
    Surface(
        color = color_secondary,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, color_primary),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Columna izquierda — info del logro
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = logro.nombre,
                    style = SETextTypes.titulo,
                    color = color_text
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = logro.descripcion,
                    style = SETextTypes.plano,
                    color = color_text
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Progreso: ${logro.progresoActual}/${logro.progresoObjetivo}",
                    style = SETextTypes.plano,
                    color = color_text
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(150.dp)) {
                when {
                    logro.recompensaReclamada -> {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "¡Reclamado!",
                                style = SETextTypes.plano,
                                color = color_fichas_verdes,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    logro.esCompletado -> {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            if (logro.imagen != 0) {
                                Image(
                                    painter = painterResource(id = logro.imagen),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(70.dp)
                                        .aspectRatio(1f),
                                    contentScale = ContentScale.Fit
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                            if (logro.valorRecompensa.isNotEmpty()) {
                                Text(
                                    text = logro.valorRecompensa,
                                    style = SETextTypes.plano,
                                    color = color_text,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                            Button(
                                onClick = { onReclamar(logro.id) },
                                colors = ButtonDefaults.buttonColors(containerColor = color_fichas_verdes)
                            ) {
                                Text("Reclamar", color = color_text)
                            }
                        }
                    }
                    else -> {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            if (logro.imagen != 0) {
                                Image(
                                    painter = painterResource(id = logro.imagen),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(70.dp)
                                        .aspectRatio(1f),
                                    contentScale = ContentScale.Fit
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                            if (logro.valorRecompensa.isNotEmpty()) {
                                Text(
                                    text = logro.valorRecompensa,
                                    style = SETextTypes.plano,
                                    color = color_text,
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