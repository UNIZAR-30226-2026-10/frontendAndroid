package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.Destinos
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_offline
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_online
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_primary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_secondary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_selected
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_text
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_unselected

data class Amigo(
    val nombre: String,
    val estadoTexto: String, // "online", "te ha invitado", etc.
    val estaOnline: Boolean,
    val haInvitado: Boolean = false
)

val listaAmigosEjemplo = listOf(
    Amigo("EscaladorMaestro", "online", true),
    Amigo("ZigZagKing", "te ha invitado", true, haInvitado = true),
    Amigo("Colmillo Veloz", "online", true),
    Amigo("Escalera77", "desconectado", false)
)

@Composable
fun AmigosScreen(SEState: SENavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cabecera, buscar amigos y volver a la pantalla anterior
        Cabecera(SEState)

        ListaAmigos(listaAmigosEjemplo)
    }
}

@Composable
fun Cabecera(SEState: SENavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Vovler a Crear Partida
        Surface(
            onClick = { SEState.goTo(Destinos.JUGAR_CREAR) },
            color = color_secondary,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(2.dp, color_primary)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Volver",
                    tint = color_text,
                    modifier = Modifier
                        .size(18.dp)
                        .graphicsLayer( scaleX = -1f ) // Espejo horizontal
                )
                Spacer(Modifier.width(8.dp))
                Text(text = "Crear partida")
            }
        }

        // Titulo
        Text(
            text = "Amigos",
            style = SETextTypes.grande,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

        // Búsqueda de amigos
        var textState by remember { mutableStateOf("") }

        Surface(
            color = color_secondary,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(2.dp, color_primary),
            modifier = Modifier
                .height(35.dp)
                .width(150.dp)
        ) {
            TextField(
                value = textState,
                onValueChange = { textState = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = color_text,
                        modifier = Modifier
                            .size(24.dp)
                            .offset(y = 6.dp)
                    )
                },
                singleLine = true,
                textStyle = SETextTypes.plano,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = color_text,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .drawBehind {
                        // Línea de subrayado manualmente
                        val strokeWidthPx = 1.dp.toPx()
                        val y = size.height - strokeWidthPx
                        drawLine(
                            color = color_text.copy(alpha = 0.5f), // Color de la línea (un poco transparente queda mejor)
                            start = Offset(x = 50.dp.toPx(), y = y), // Empezamos después de la lupa
                            end = Offset(x = size.width - 20.dp.toPx(), y = y), // Terminamos antes del final
                            strokeWidth = strokeWidthPx
                        )
                    }
            )
        }
    }
}

@Composable
fun ListaAmigos(amigos: List<Amigo>) {
    // Guarda de forma observable el amigo del cual se estan viendo los detalles
    var amigoExpandido by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        items(amigos) { amigo ->
            TarjetaAmigo(amigo, amigoExpandido == amigo.nombre, onClick = {
                    amigoExpandido = if (amigoExpandido == amigo.nombre) null
                    else amigo.nombre
                })
        }
    }
}

@Composable
fun TarjetaAmigo(amigo: Amigo, expandido: Boolean, onClick: () -> Unit) {
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
                desplegableAmigo(amigo)
            }
        }

    }
}

@Composable
fun informacionAmigo(amigo: Amigo, expandido: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { onClick() }
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
                color = if (amigo.estaOnline) color_online else color_offline,
                modifier = Modifier.size(20.dp)
            ) {}
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Nombre y si ha invitado o no
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = amigo.nombre,
                style = SETextTypes.plano,
                color = color_text
            )
            Text(
                text = amigo.estadoTexto,
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
fun desplegableAmigo(amigo: Amigo) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color_secondary.copy(alpha = 0.5f)) // Un tono ligeramente distinto
            .padding(bottom = 8.dp, start = 16.dp, end = 16.dp)
    ) {

        val colorUnirse = if (amigo.haInvitado) color_text
            else color_unselected

        val acciones = listOf(
            Triple(Icons.Default.AddCircle, "Invitar a la partida", color_text),
            Triple(Icons.Default.PlayArrow, "Unirse a la partida", colorUnirse),
            Triple(Icons.Default.Email, "Enviar un mensaje", color_text),
            Triple(Icons.Default.Delete, "Borrar amigo", color_text)
        )

        acciones.forEach { (icono, texto, color) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* Acción aquí */ }
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(icono, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text(texto, style = SETextTypes.plano, color = color)
            }
        }
    }
}