package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Perfil

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CategoriaCosmetico
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.LogoutBoton
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.Destinos
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_primary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_secondary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_text

@Composable
fun PerfilScreen(navHost: SENavHostController, viewModel: PerfilViewModel) {
    val perfil = viewModel.perfil
    val cargando = viewModel.cargando
    val errorMessage = viewModel.errorMessage
    val context = LocalContext.current

    val nombre = perfil?.nombre ?: ""
    val stats = if (perfil != null) "${perfil.victorias}W/${perfil.derrotas}L" else ""

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
        nombre = nombre,
        stats = stats,
        skinsEscalera = viewModel.skinsEscalera,
        skinsSerpiente = viewModel.skinsSerpiente,
        skinsFicha = viewModel.skinsFicha,
        iconos = viewModel.iconos,
        skinEscaleraActual = perfil?.skinEscaleraActual ?: "",
        skinSerpienteActual = perfil?.skinSerpienteActual ?: "",
        skinFichaActual = perfil?.skinFichaActual ?: "",
        iconoActual = perfil?.iconoActual ?: "",
        onNombreConfirmado = { nuevo -> viewModel.actualizarNombre(nuevo) },
        onIconoSeleccionado     = { iconId -> viewModel.actualizarIcono(iconId) },
        onCosmeticoSeleccionado = { categoria, skinId ->
                viewModel.actualizarCosmetico(categoria, skinId)
            },
        onCerrarSesion = { viewModel.cerrarSesion(context) { navHost.goTo(Destinos.LOGIN) } }
        )
}

@Composable
fun PerfilContent(
    nombre: String,
    stats: String,
    iconoActual: String,
    iconos: List<String>,
    skinsEscalera: List<String>,
    skinsSerpiente: List<String>,
    skinsFicha: List<String>,
    skinEscaleraActual: String,
    skinSerpienteActual: String,
    skinFichaActual: String,
    onNombreConfirmado: (String) -> Unit,
    onIconoSeleccionado: (String) -> Unit,
    onCosmeticoSeleccionado: (CategoriaCosmetico, String) -> Unit,
    onCerrarSesion: () -> Unit
) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        color = color_bg
    ) {
        Column {
            TarjetaUsuario(
                nombre = nombre,
                stats = stats,
                iconoActual = iconoActual,
                iconos = iconos,
                onNombreConfirmado = onNombreConfirmado,
                onIconoSeleccionado = onIconoSeleccionado,
                onCerrarSesion = onCerrarSesion
            )
            Spacer(modifier = Modifier.height(15.dp))
            SeccionCosmeticos(
                skinsEscalera = skinsEscalera,
                skinsSerpiente = skinsSerpiente,
                skinsFicha = skinsFicha,
                skinEscaleraActual = skinEscaleraActual,
                skinSerpienteActual = skinSerpienteActual,
                skinFichaActual = skinFichaActual,
                onCosmeticoSeleccionado = onCosmeticoSeleccionado
            )
        }
    }
}

@Composable
fun TarjetaUsuario(
    nombre: String,
    stats: String,
    iconoActual: String,
    iconos: List<String>,
    onNombreConfirmado: (String) -> Unit,
    onIconoSeleccionado: (String) -> Unit,
    onCerrarSesion: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = color_secondary,
        border = BorderStroke(2.dp, color_primary),
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(modifier = Modifier.padding(8.dp)) {

            // Esquina superior derecha: stats encima, botón logout debajo
            Column(
                modifier = Modifier.align(Alignment.TopEnd),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = stats,
                    style = SETextTypes.grande,
                    color = color_text
                )
                Spacer(modifier = Modifier.height(8.dp))
                LogoutBoton(onCerrarSesion)
            }

            // Fila con avatar + etiqueta + caja nombre
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
            ) {
                AvatarUsuario(
                    iconoActual         = iconoActual,
                    iconos              = iconos,
                    onIconoSeleccionado = onIconoSeleccionado
                )
                Spacer(modifier = Modifier.width(16.dp))
                EtiquetaNombre()
                CajaNombreUsuario(nombre, onNombreConfirmado)
            }
        }
    }
}

@Composable
fun AvatarUsuario(
    iconoActual: String,
    iconos: List<String>,
    onIconoSeleccionado: (String) -> Unit
) {
    var mostrarMenu by remember { mutableStateOf(false) }

    Box(contentAlignment = Alignment.BottomEnd) {
        Surface(
            modifier = Modifier.size(85.dp),
            shape = CircleShape,
            color = color_text,
            border = BorderStroke(2.dp, color_primary)
        ) {
            Image(
                painter = painterResource(id = obtenerImagenCosmetico(iconoActual, CategoriaCosmetico.ICONO)),
                contentDescription = "Avatar",
                modifier = Modifier.padding(4.dp)
            )
        }

        IconButton(
            onClick = { mostrarMenu = true },
            modifier = Modifier
                .size(24.dp)
                .offset(x = 2.dp, y = 2.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Cambiar avatar",
                tint = color_text,
                modifier = Modifier.size(18.dp)
            )
        }

        DropdownMenu(
            expanded = mostrarMenu,
            onDismissRequest = { mostrarMenu = false }
        ) {
            iconos.forEach { iconId ->
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = obtenerImagenCosmetico(iconId, CategoriaCosmetico.ICONO)),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp).padding(end = 8.dp)
                            )
                            Text(
                                text = iconId.replace("icono_", "").replace("_", " "),
                                color = if (iconId == iconoActual) color_primary else color_text
                            )
                        }
                    },
                    onClick = {
                        mostrarMenu = false
                        onIconoSeleccionado(iconId)
                    }
                )
            }
        }
    }
}

@Composable
fun CajaNombreUsuario(nombreActual: String, onConfirmar: (String) -> Unit) {
    var editando by remember { mutableStateOf(false) }
    var textoTemporal by remember(nombreActual) { mutableStateOf(nombreActual) }

    Surface(
        modifier = Modifier
            .padding(start = 20.dp)
            .width(300.dp)
            .height(40.dp),
        color = color_bg,
        border = BorderStroke(1.dp, if (editando) color_primary else color_text),
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (editando) {
                BasicTextField(
                    value = textoTemporal,
                    onValueChange = { textoTemporal = it },
                    textStyle = SETextTypes.plano.copy(fontSize = 18.sp, color = color_text),
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    cursorBrush = SolidColor(color_primary)
                )
                IconButton(
                    onClick = {
                        editando = false
                        onConfirmar(textoTemporal)
                    },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Confirmar guardado",
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(20.dp)
                    )
                }
            } else {
                Text(
                    text = nombreActual,
                    style = SETextTypes.plano.copy(fontSize = 18.sp),
                    color = color_text,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = { editando = true },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar nombre",
                        tint = color_text,
                        modifier = Modifier.size(18.dp)
                    )
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
            color = color_text,
            border = BorderStroke(2.dp, Color.Black)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icono_jugador_default),
                contentDescription = null,
                modifier = Modifier.padding(4.dp)
            )
        }
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Cambiar avatar",
            tint = color_text,
            modifier = Modifier
                .size(24.dp)
                .offset(x = 2.dp, y = 2.dp)
                .padding(4.dp)
        )
    }
}

@Composable
fun EtiquetaNombre() {
    Text(
        text = "Nombre de usuario:",
        style = SETextTypes.seleccionable.copy(fontSize = 18.sp),
        color = color_text
    )
}

@Composable
fun SeccionCosmeticos(
    skinsEscalera: List<String>,
    skinsSerpiente: List<String>,
    skinsFicha: List<String>,
    skinEscaleraActual: String,
    skinSerpienteActual: String,
    skinFichaActual: String,
    onCosmeticoSeleccionado: (CategoriaCosmetico, String) -> Unit
) {
    Column {
        Text(
            text = "Cosméticos",
            style = SETextTypes.grande.copy(fontSize = 25.sp),
            color = color_text,
            modifier = Modifier.padding(start = 50.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CosmeticoItem(
                label = "Escaleras",
                categoria = CategoriaCosmetico.ESCALERA,
                skinActual = skinEscaleraActual,
                opciones = skinsEscalera,
                onSeleccion = { skinId ->
                    onCosmeticoSeleccionado(
                        CategoriaCosmetico.ESCALERA,
                        skinId
                    )
                }
            )
            CosmeticoItem(
                label = "Serpientes",
                categoria = CategoriaCosmetico.SERPIENTE,
                skinActual = skinSerpienteActual,
                opciones = skinsSerpiente,
                onSeleccion = { skinId ->
                    onCosmeticoSeleccionado(
                        CategoriaCosmetico.SERPIENTE,
                        skinId
                    )
                }
            )
            CosmeticoItem(
                label = "Fichas",
                categoria = CategoriaCosmetico.FICHA,
                skinActual = skinFichaActual,
                opciones = skinsFicha,
                onSeleccion = { skinId ->
                    onCosmeticoSeleccionado(
                        CategoriaCosmetico.FICHA,
                        skinId
                    )
                }
            )
        }
    }
}

@Composable
fun CosmeticoItem(
    label: String,
    categoria: CategoriaCosmetico,
    skinActual: String,
    opciones: List<String>,
    onSeleccion: (String) -> Unit
) {
    var mostrarMenu by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, style = SETextTypes.grande, color = color_text)
        Spacer(modifier = Modifier.height(4.dp))
        Box(contentAlignment = Alignment.Center) {
            Surface(
                modifier = Modifier
                    .size(240.dp, 145.dp)
                    .border(2.dp, color_primary, RoundedCornerShape(4.dp)),
                color = color_bg,
                shape = RoundedCornerShape(4.dp)
            ) {
                Image(
                    painter = painterResource(id = obtenerImagenCosmetico(skinActual, categoria)),
                    contentDescription = label,
                    modifier = Modifier.padding(8.dp),
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.4f), BlendMode.Darken)
                )
                IconButton(
                    onClick = { mostrarMenu = true },
                    modifier = Modifier.size(60.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar $label",
                        tint = color_text,
                        modifier = Modifier.size(60.dp)
                    )
                }
            }
            DropdownMenu(
                expanded = mostrarMenu,
                onDismissRequest = { mostrarMenu = false }
            ) {
                opciones.forEach { skinId ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = skinId,
                                color = if (skinId == skinActual) color_primary else color_text
                            )
                        },
                        onClick = {
                            mostrarMenu = false
                            onSeleccion(skinId)
                        }
                    )
                }
            }
        }
    }
}

fun obtenerImagenCosmetico(id: String, cat: CategoriaCosmetico): Int {
    return when (id) {
        "icono_nerd" -> R.drawable.icono_jugador_nerd
        "icono_completista" -> R.drawable.icono_jugador_completista
        "icono_platino" -> R.drawable.icono_jugador_platino
        "serpiente_calcetin" -> R.drawable.serpiente_calcetin
        "serpiente_futuro" -> R.drawable.serpiente_futuro
        "ficha_totem" -> R.drawable.jugador_azul_totem
        "ficha_esqueleto" -> R.drawable.jugador_verde_calavera
        "icono_default" -> R.drawable.icono_jugador_default
        "serpiente_default" -> R.drawable.serpiente
        "ficha_default" -> R.drawable.jugador_rojo_totem
        "escalera"          -> R.drawable.escalera
        "escalera2"         -> R.drawable.escalera2
        "escalera_estratega" -> R.drawable.escalera_estratega
        "escalera_jungla"    -> R.drawable.escalera_jungla
        "escalera_magnate"   -> R.drawable.escalera_magnate
        else -> when(cat) {
            CategoriaCosmetico.ICONO -> R.drawable.icono_jugador_default
            CategoriaCosmetico.SERPIENTE -> R.drawable.serpiente
            CategoriaCosmetico.FICHA -> R.drawable.jugador_rojo_totem
            CategoriaCosmetico.ESCALERA  -> R.drawable.escalera
            else -> R.drawable.tablero_debug
        }
    }
}