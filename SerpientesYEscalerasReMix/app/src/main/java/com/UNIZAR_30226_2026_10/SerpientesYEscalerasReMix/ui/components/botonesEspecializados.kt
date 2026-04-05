package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.BotonGenerico
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_offline
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_online
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_sf


@Composable
fun BotonNuevoMazo(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BotonGenerico(
        texto = "Nuevo mazo",
        onClick = onClick,
        modifier = modifier,
        colorPrincipal = color_online,
        icono = null //FIXME
    )
}

@Composable
fun BotonEditarMazo(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BotonGenerico(
        texto = "Editar mazo",
        onClick = onClick,
        modifier = modifier,
        colorPrincipal = color_sf,
        icono = null //FIXME
    )
}

@Composable
fun BotonEliminarMazo(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BotonGenerico(
        texto = "Eliminar mazo",
        onClick = onClick,
        modifier = modifier,
        colorPrincipal = color_offline,
        icono = null //FIXME
    )
}