package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Logros

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.*
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.LogroUsuario

@Composable
fun LogrosScreen(SEState: SENavHostController, viewModel: LogrosViewModel) {
    // Observamos el estado real del ViewModel
    val listaDeLogros = viewModel.logros
    val estaCargando = viewModel.cargando
    val error = viewModel.errorMessage

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
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (logro.recompensaReclamada) "${logro.nombre} (Reclamado)" else logro.nombre,
                    style = SETextTypes.titulo,
                    color = color_text
                )

                Text(text = "Descripción:", style = SETextTypes.seleccionable, modifier = Modifier.padding(vertical = 4.dp))
                Text(text = logro.descripcion, style = SETextTypes.plano)

                Text(text = "Progreso:", style = SETextTypes.seleccionable, modifier = Modifier.padding(vertical = 4.dp))
                Text(
                    text = "(${logro.progresoActual}/${logro.progresoObjetivo})",
                    style = if (logro.esCompletado) SETextTypes.sombreado else SETextTypes.plano
                )

                // Botón para reclamar según la API (POST /api/users/{email}/achievements)
                if (logro.esCompletado && !logro.recompensaReclamada) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { onReclamar(logro.id) },
                        colors = ButtonDefaults.buttonColors(containerColor = color_primary)
                    ) {
                        Text("Reclamar", color = color_secondary)
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.width(80.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Recompensa", style = SETextTypes.seleccionable)
                if (logro.tipoRecompensa == "Moneda") {
                    Box(modifier = Modifier.height(80.dp), contentAlignment = Alignment.Center) {
                        Text(text = "${logro.valorRecompensa} Sep", style = SETextTypes.plano)
                    }
                } else {
                    Box(modifier = Modifier.width(60.dp).aspectRatio(3f/4f).background(color_selectedText)) {
                        Image(
                            painter = painterResource(id = if(logro.imagen != 0) logro.imagen else R.drawable.tablero_debug),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
        }
    }
}