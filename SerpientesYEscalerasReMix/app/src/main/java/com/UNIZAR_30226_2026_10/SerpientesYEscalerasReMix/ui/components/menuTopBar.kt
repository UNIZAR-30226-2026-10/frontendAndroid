package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.*

@Composable
fun MenuTopBar() {
    var selectedTab by remember { mutableStateOf("Jugar") }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        TabItem(R.drawable.jugar_icon, "Jugar", Modifier.weight(1f))
        TabItem(R.drawable.jugar_icon, "Mazos", Modifier.weight(1f))
        TabItem(R.drawable.jugar_icon, "Logros", Modifier.weight(1f))
        TabItem(R.drawable.jugar_icon, "Tienda", Modifier.weight(1f))
        TabItem(R.drawable.jugar_icon, "Perfil", Modifier.weight(1f))
    }

}

@Composable
fun TabItem(icono: Int, titulo: String, mod: Modifier) {
    Box(
        modifier = mod
            .height(40.dp)
            .border(width = 2.dp, color = color_primary)
            .background(color = color_secondary),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono del item
            Image(
                painter = painterResource(id = icono),
                contentDescription = "Icono de $titulo",
                modifier = Modifier.size(25.dp)
            )

            Spacer(modifier = Modifier.width(3.dp)) // Espacio de separación entre ellos

            // Titulo del item
            Text(
                text = titulo,
                style = SerpientesYEscalerasType.tab
            )
        }
    }

}