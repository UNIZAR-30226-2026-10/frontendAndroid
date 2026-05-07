package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Perfil

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CategoriaCosmetico
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.LogoutBoton
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.Destinos
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.*

@Composable
fun PerfilScreen(navHost: SENavHostController, viewModel: PerfilViewModel) {
    val perfil = viewModel.perfil
    val cargando = viewModel.cargando
    val errorMessage = viewModel.errorMessage

    if (cargando) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = color_primary)
        }
        return
    }

    if (errorMessage != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = errorMessage, color = Color.Red, style = SETextTypes.plano)
        }
        return
    }

    PerfilContent(
        nombre = perfil?.nombre ?: "",
        stats = if (perfil != null) "${perfil.victorias}W / ${perfil.derrotas}L" else "",
        skinsEscalera = viewModel.skinsEscalera,
        skinsSerpiente = viewModel.skinsSerpiente,
        skinsFicha = viewModel.skinsFicha,
        skinEscaleraActual = perfil?.skinEscaleraActual ?: "Estándar",
        skinSerpienteActual = perfil?.skinSerpienteActual ?: "Estándar",
        skinFichaActual = perfil?.skinFichaActual ?: "Estándar",
        onNombreConfirmado = { nuevo -> viewModel.actualizarNombre(nuevo) },
        onCosmeticoSeleccionado = { cat, id -> viewModel.actualizarCosmetico(cat, id) },
        onCerrarSesion = { viewModel.cerrarSesion { navHost.goTo(Destinos.LOGIN) } }
    )
}

@Composable
fun PerfilContent(
    nombre: String,
    stats: String,
    skinsEscalera: List<String>,
    skinsSerpiente: List<String>,
    skinsFicha: List<String>,
    skinEscaleraActual: String,
    skinSerpienteActual: String,
    skinFichaActual: String,
    onNombreConfirmado: (String) -> Unit,
    onCosmeticoSeleccionado: (CategoriaCosmetico, String) -> Unit,
    onCerrarSesion: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = color_bg // Fondo azul oscuro general
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
            // CABECERA: Perfil con borde amarillo
            TarjetaUsuario(nombre, stats, onNombreConfirmado, onCerrarSesion)

            Spacer(modifier = Modifier.height(24.dp))

            // SECCIÓN COSMÉTICOS
            Text(
                text = "Cosméticos:",
                style = SETextTypes.grande.copy(fontSize = 25.sp, color = color_text),
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CosmeticoItem("Escaleras", R.drawable.tablero_debug, skinEscaleraActual, skinsEscalera) {
                    onCosmeticoSeleccionado(CategoriaCosmetico.ESCALERA, it)
                }
                CosmeticoItem("Serpientes", R.drawable.tablero_debug, skinSerpienteActual, skinsSerpiente) {
                    onCosmeticoSeleccionado(CategoriaCosmetico.SERPIENTE, it)
                }
                CosmeticoItem("Fichas", R.drawable.tablero_debug, skinFichaActual, skinsFicha) {
                    onCosmeticoSeleccionado(CategoriaCosmetico.FICHA, it)
                }
            }
        }
    }
}

@Composable
fun TarjetaUsuario(
    nombre: String,
    stats: String,
    onNombreConfirmado: (String) -> Unit,
    onCerrarSesion: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = color_secondary,
        border = BorderStroke(2.dp, color_primary),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                AvatarUsuario()
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Nombre de usuario:",
                        style = SETextTypes.seleccionable.copy(fontSize = 14.sp),
                        color = color_text.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    CajaNombreUsuario(nombre, onNombreConfirmado)
                }
            }

            Column(horizontalAlignment = Alignment.End, modifier = Modifier.padding(start = 12.dp)) {
                Text(text = stats, style = SETextTypes.grande.copy(fontSize = 18.sp), color = color_text)
                Spacer(modifier = Modifier.height(8.dp))
                LogoutBoton(onCerrarSesion)
            }
        }
    }
}

@Composable
fun CajaNombreUsuario(nombreActual: String, onConfirmar: (String) -> Unit) {
    var editando by remember { mutableStateOf(false) }
    var textoTemporal by remember(nombreActual) { mutableStateOf(nombreActual) }

    Surface(
        modifier = Modifier.widthIn(max = 350.dp).height(42.dp),
        color = Color(0xFF1A1C2E), // Fondo oscuro para la caja
        border = BorderStroke(2.dp, if (editando) color_primary else color_text),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(modifier = Modifier.padding(horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically) {
            if (editando) {
                BasicTextField(
                    value = textoTemporal,
                    onValueChange = { textoTemporal = it },
                    textStyle = SETextTypes.plano.copy(fontSize = 18.sp, color = color_text),
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    cursorBrush = SolidColor(color_primary)
                )
                IconButton(onClick = { editando = false; onConfirmar(textoTemporal) }) {
                    Icon(Icons.Default.Check, "Guardar", tint = Color(0xFF4CAF50))
                }
            } else {
                Text(
                    text = nombreActual,
                    style = SETextTypes.seleccionable.copy(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                    color = color_text,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { editando = true }, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.Edit, "Editar", tint = color_text.copy(alpha = 0.6f), modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

@Composable
fun AvatarUsuario() {
    Box(contentAlignment = Alignment.BottomEnd) {
        Surface(
            modifier = Modifier.size(85.dp),
            shape = CircleShape,
            color = Color.White,
            border = BorderStroke(2.dp, color_primary)
        ) {
            Image(painter = painterResource(id = R.drawable.icono_default), contentDescription = null, modifier = Modifier.padding(4.dp))
        }
        Surface(modifier = Modifier.size(26.dp), color = color_primary, shape = CircleShape, border = BorderStroke(2.dp, Color.White)) {
            Icon(Icons.Default.Edit, null, tint = Color.White, modifier = Modifier.padding(5.dp))
        }
    }
}

@Composable
fun CosmeticoItem(label: String, imagenRes: Int, skinActual: String, opciones: List<String>, onSeleccion: (String) -> Unit) {
    var mostrarMenu by remember { mutableStateOf(false) }
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(180.dp)) {
        Text(text = label, style = SETextTypes.grande.copy(fontSize = 18.sp, color = color_text))
        Spacer(modifier = Modifier.height(8.dp))
        Surface(
            modifier = Modifier.height(115.dp).fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            color = color_bg,
            border = BorderStroke(2.dp, color_primary),
            onClick = { mostrarMenu = true }
        ) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = imagenRes),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.7f), BlendMode.Darken)
                )
                Icon(Icons.Default.Edit, null, tint = Color.White, modifier = Modifier.size(40.dp))
                Surface(modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(), color = color_primary.copy(alpha = 0.8f)) {
                    Text(text = skinActual, style = SETextTypes.plano.copy(fontSize = 12.sp, color = Color.White), modifier = Modifier.padding(vertical = 2.dp), textAlign = TextAlign.Center)
                }
            }
        }
        DropdownMenu(expanded = mostrarMenu, onDismissRequest = { mostrarMenu = false }) {
            opciones.forEach { DropdownMenuItem(text = { Text(it) }, onClick = { onSeleccion(it); mostrarMenu = false }) }
        }
    }
}
