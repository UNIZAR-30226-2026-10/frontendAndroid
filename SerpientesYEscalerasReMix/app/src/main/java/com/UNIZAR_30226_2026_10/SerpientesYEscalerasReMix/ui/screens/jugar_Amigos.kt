package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_offline
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_online
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

@Preview
@Composable
fun AmigosScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cabecera y buscar amigos
        Text(
            text = "Amigos",
            style = SETextTypes.grande,
            modifier = Modifier.padding(16.dp)
        )

        ListaAmigos(listaAmigosEjemplo)
    }
}

@Composable
fun ListaAmigos(amigos: List<Amigo>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        items(amigos) { amigo ->
            TarjetaAmigo(amigo)
        }
    }
}

@Composable
fun TarjetaAmigo(amigo: Amigo, seleccionado: Boolean) {
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
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
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
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = color_text
            )
        }
    }
}