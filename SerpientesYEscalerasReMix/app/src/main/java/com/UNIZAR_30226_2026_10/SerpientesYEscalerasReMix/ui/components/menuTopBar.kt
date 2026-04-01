package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.Destinos
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_primary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_secondary
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_selected

@Composable
fun MenuTopBar(SEState: SENavHostController) {
    val tabSeleccionado = SEState.rutaActual()
    val pantallasSinTopBar = listOf(Destinos.LOGIN, Destinos.REGISTER)

    if (tabSeleccionado !in pantallasSinTopBar) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TabItem(
                R.drawable.jugar_icon,
                "Jugar",
                tabSeleccionado == Destinos.JUGAR_CREAR || tabSeleccionado == Destinos.JUGAR_CONTINUAR || tabSeleccionado == Destinos.JUGAR_AMIGOS,
                { SEState.goTo(Destinos.JUGAR_CREAR) },
                Modifier.weight(1f)
            )

            TabItem(
                R.drawable.jugar_icon,
                "Mazos",
                tabSeleccionado == Destinos.MAZOS,
                { SEState.goTo(Destinos.MAZOS) },
                Modifier.weight(1f)
            )

            TabItem(
                R.drawable.jugar_icon,
                "Logros",
                tabSeleccionado == Destinos.LOGROS,
                { SEState.goTo(Destinos.LOGROS) },
                Modifier.weight(1f)
            )

            TabItem(
                R.drawable.jugar_icon,
                "Tienda",
                tabSeleccionado == Destinos.TIENDA,
                { SEState.goTo(Destinos.TIENDA) },
                Modifier.weight(1f)
            )

            TabItem(
                R.drawable.jugar_icon,
                "Perfil",
                tabSeleccionado == Destinos.PERFIL,
                { SEState.goTo(Destinos.PERFIL) },
                Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun TabItem(icono: Int, titulo: String, seleccionado: Boolean, onClick: () -> Unit, mod: Modifier = Modifier) {

    val colorBox = if (seleccionado) color_selected else color_secondary

    Box(
        modifier = mod
            .height(50.dp)
            .border(width = 2.dp, color = color_primary)
            .background(color = colorBox)
            .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { onClick() },
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

            Spacer(modifier = Modifier.width(3.dp))

            // Titulo del item
            Text(
                text = titulo,
                style = SETextTypes.tab
            )
        }
    }

}