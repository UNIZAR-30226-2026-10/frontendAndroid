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
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Usuario
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_selected
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_text
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_unselected


@Composable
fun ListaAmigos(
    usuarios: List<Usuario>,
    onInvitar: (String) -> Unit,
    onUnirse: (String) -> Unit,
    onBorrar: (String) -> Unit
) {
    var usuarioExpandido by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        items(usuarios) { usuario ->
            TarjetaAmigo(
                amigo = usuario,
                expandido = usuarioExpandido == usuario.nombre,
                onClick = {
                    usuarioExpandido = if (usuarioExpandido == usuario.nombre) null
                    else usuario.nombre
                },
                onInvitar = onInvitar,
                onUnirse = onUnirse,
                onBorrar = onBorrar
            )
        }
    }
}

@Composable
fun TarjetaAmigo(
    amigo: Usuario,
    expandido: Boolean,
    onClick: () -> Unit,
    onInvitar: (String) -> Unit,
    onUnirse: (String) -> Unit,
    onBorrar: (String) -> Unit
) {
    val fondo = if (amigo.haInvitado) color_selected
    else color_unselected

    Surface(
        color = fondo,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        shadowElevation = 4.dp
    ) {
        Column {
            InformacionAmigo(amigo, expandido, onClick)

            if (expandido) {
                DesplegableAmigo(
                    usuario = amigo,
                    fondo = fondo,
                    onInvitar = onInvitar,
                    onUnirse = onUnirse,
                    onBorrar = onBorrar
                )
            }
        }
    }
}

@Composable
fun InformacionAmigo(usuario: Usuario, expandido: Boolean, onClick: () -> Unit) {
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
        }

        Spacer(modifier = Modifier.width(16.dp))

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

        val rotacion = if (expandido) 90f else 0f
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
fun DesplegableAmigo(
    usuario: Usuario,
    fondo: Color,
    onInvitar: (String) -> Unit,
    onUnirse: (String) -> Unit,
    onBorrar: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(fondo)
            .padding(bottom = 8.dp, start = 16.dp, end = 16.dp)
    ) {
        val colorUnselectedAux = if (fondo == color_unselected) color_bg else color_unselected
        val colorUnirse = if (usuario.haInvitado) color_text else colorUnselectedAux

        DesplegableItem(Icons.Default.AddCircle, "Invitar a la partida", color_text) {
            onInvitar(usuario.nombre)
        }
        DesplegableItem(Icons.Default.PlayArrow, "Unirse a la partida", colorUnirse) {
            if (usuario.haInvitado) onUnirse(usuario.nombre)
        }
        DesplegableItem(Icons.Default.Delete, "Borrar amigo", color_text) {
            onBorrar(usuario.nombre)
        }

    }
}

@Composable
fun DesplegableItem(
    icono: androidx.compose.ui.graphics.vector.ImageVector,
    texto: String,
    color: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
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
