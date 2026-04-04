package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.Usuario
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.Destinos
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Jugar_Amigos.AmigosViewModel
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_offline
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_positive
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_secondary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_selected
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_text
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_unselected
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun ListaAmigos(
    viewModel: AmigosViewModel,
    SEState: SENavHostController,
    snackHost: SnackbarHostState,
    usuarios: List<Usuario>
) {
    // Guarda de forma observable el amigo del cual se estan viendo los detalles
    var usuarioExpandido by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        items(usuarios) { usuario ->
            TarjetaAmigo(
                viewModel,
                SEState,
                snackHost,
                usuario,
                usuarioExpandido == usuario.nombre,
                onClick = {
                    usuarioExpandido = if (usuarioExpandido == usuario.nombre) null
                    else usuario.nombre
                })
        }
    }
}

@Composable
fun TarjetaAmigo(
    viewModel: AmigosViewModel,
    SEState: SENavHostController,
    snackHost: SnackbarHostState,
    amigo: Usuario,
    expandido: Boolean,
    onClick: () -> Unit
) {
    // Si te ha invitado, se usa azul claro
    val fondo = if (amigo.haInvitado) color_selected
    else if (amigo.estaOnline) color_secondary
    else color_unselected

    Surface(
        color = fondo,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        shadowElevation = 4.dp
    ) {
        Column() {
            informacionAmigo(amigo, expandido, onClick)

            if (expandido) {
                desplegableAmigo(viewModel, SEState, snackHost, amigo, fondo)
            }
        }

    }
}

@Composable
fun informacionAmigo(usuario: Usuario, expandido: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() }
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar con indicador de estado
        Box(contentAlignment = Alignment.BottomEnd) {
            Image(
                painter = painterResource(id = R.drawable.amigos),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = CircleShape,
                        clip = false
                    )
                    .clip(CircleShape)
                    .border(1.dp, color_bg, CircleShape)
            )
            // Círculo de estado (Verde/Rojo)
            Surface(
                shape = CircleShape,
                color = if (usuario.estaOnline) color_positive else color_offline,
                modifier = Modifier.size(20.dp)
            ) {}
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Nombre y si ha invitado o no
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = usuario.nombre,
                style = SETextTypes.plano,
                color = color_text
            )
            Text(
                text = usuario.estadoTexto,
                style = SETextTypes.sombreado,
                color = color_text.copy(alpha = 0.7f)
            )
        }

        // Icono de flecha
        // rotación de la flecha (en caso de que se haber extendio el item)
        var rotacion = if (expandido) 90f
        else 0f
        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
                .rotate(rotacion),
            tint = color_text
        )
    }
}

@Composable
fun desplegableAmigo(
    viewModel: AmigosViewModel,
    SEState: SENavHostController,
    snackHost: SnackbarHostState,
    usuario: Usuario,
    fondo: Color
) {

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(fondo)
            .padding(bottom = 8.dp, start = 16.dp, end = 16.dp)
    ) {

        val colorUnselectedAux = if (fondo == color_unselected) color_bg
        else color_unselected

        val colorUnirse = if (usuario.haInvitado) color_text
        else colorUnselectedAux

        val acciones =
            if (usuario.esAmigo) listOf(
                Triple(Icons.Default.AddCircle, "Invitar a la partida", color_text),
                Triple(Icons.Default.PlayArrow, "Unirse a la partida", colorUnirse),
                Triple(Icons.Default.Delete, "Borrar amigo", color_text)
            )
            else listOf(
                Triple(Icons.Default.Add, "Añadir amigo", color_text)
            )

        acciones.forEach { (icono, texto, color) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = {
                        desplegableOptionAccion(viewModel, SEState, snackHost, scope, texto, usuario)
                    })
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    icono,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(texto, style = SETextTypes.plano, color = color)
            }
        }
    }
}

fun desplegableOptionAccion(
    viewModel: AmigosViewModel,
    SEState: SENavHostController,
    snackHost: SnackbarHostState,
    scope: CoroutineScope,
    texto: String,
    usuario: Usuario
) {
    if (texto == "Invitar a la partida") { // TODO cambiar y enviar el e-mail del usuario en las peticiones
        viewModel.invitarAmigo(usuario.nombre)
    } else if (texto == "Unirse a la partida") {
        if (usuario.haInvitado) {
            viewModel.unirseAPartida(
                usuario.nombre,
                onSuccess = { SEState.goTo(Destinos.JUGAR_CREAR) },
                onError = {
                    scope.launch {
                        snackHost.showSnackbar(
                            message = "No te has podido unir al lobby de ${usuario.nombre}",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            )
        }
    } else if (texto == "Borrar amigo") {
        viewModel.borrarAmigo(usuario.nombre)
    } else if (texto == "Añadir amigo") {
        viewModel.anadirAmigo(usuario.nombre)
    }
}
