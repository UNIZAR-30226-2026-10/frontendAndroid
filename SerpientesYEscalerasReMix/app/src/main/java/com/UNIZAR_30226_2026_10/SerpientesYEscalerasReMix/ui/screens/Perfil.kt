package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.*

@Composable
fun Perfil(_SEState: SENavHostController) {
    val nombreSimulado = "SerpienteGanadora5"
    val statsSimuladas = "35W/12L"

    PerfilContent(nombre = nombreSimulado, stats = statsSimuladas)
}

@Composable
fun PerfilContent(nombre: String, stats: String) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        color = color_bg,
        border = BorderStroke(2.dp, color_primary),
        shape = RoundedCornerShape(28.dp)
    ) {
        Column{
            TarjetaUsuario(nombre, stats)

            Spacer(modifier = Modifier.height(15.dp))

            SeccionCosmeticos()
        }
    }
}

@Composable
fun TarjetaUsuario(nombre: String, stats: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = color_bg,
        border = BorderStroke(2.dp, color_primary),
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(modifier = Modifier.padding(8.dp)) {
            Text(
                text = stats,
                style = SETextTypes.grande,
                color = color_text,
                modifier = Modifier.align(Alignment.TopEnd)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
            ) {
                AvatarUsuario()
                Spacer(modifier = Modifier.width(16.dp))
                EtiquetaNombre()
                Spacer(modifier = Modifier.width(16.dp))
                CajaNombreUsuario(nombre)
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
            border = BorderStroke(2.dp, Color.Black)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icono_default),
                contentDescription = null,
                modifier = Modifier.padding(4.dp)
            )
        }
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = null,
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
    Column {
        Text(
            text = "Nombre de usuario:",
            style = SETextTypes.seleccionable.copy(fontSize = 18.sp),
            color = color_text
        )
    }
}

@Composable
fun CajaNombreUsuario(nombre: String) {
    Surface(
        modifier = Modifier
            .padding(start = 20.dp)
            .width(460.dp)
            .height(40.dp),
        color = color_bg,
        border = BorderStroke(1.dp, color_text),
        shape = RoundedCornerShape(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = nombre,
                style = SETextTypes.plano.copy(fontSize = 18.sp),
                color = color_text,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Editar nombre",
                tint = color_text,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun SeccionCosmeticos() {
    Column {
        Text(
            text = "Cosmeticos:",
            style = SETextTypes.grande.copy(fontSize = 25.sp),
            color = color_text,
            modifier = Modifier.padding(start = 50.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CosmeticoItem("Escaleras", R.drawable.tablero_debug)
            CosmeticoItem("Serpientes", R.drawable.icono_default)
            CosmeticoItem("Fichas", R.drawable.tablero_debug)
        }
    }
}

@Composable
fun CosmeticoItem(label: String, imagenRes: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, style = SETextTypes.grande, color = color_text)
        Spacer(modifier = Modifier.height(4.dp))
        Box(contentAlignment = Alignment.Center) {
            Surface(
                modifier = Modifier.size(200.dp, 115.dp),
                color = Color.White,
                shape = RoundedCornerShape(4.dp)
            ) {
                Image(
                    painter = painterResource(id = imagenRes),
                    contentDescription = label,
                    modifier = Modifier.padding(12.dp),
                    contentScale = ContentScale.Fit
                )
            }
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                tint = color_primary,
                modifier = Modifier
                    .size(48.dp)
                    .rotate(-15f)
            )
        }
    }
}