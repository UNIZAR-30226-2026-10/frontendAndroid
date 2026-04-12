package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Tienda

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Producto
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.fakes.listaDePruebas
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.BotonCategoriaCustom
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.TarjetaProductoTienda
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.rememberSEAppState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.TiendaRepository
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_SEPText
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_sf
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_text
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fondoTienda
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.DetalleProductoTienda


@Composable
fun TiendaScreen(SEState: SENavHostController, viewModel: TiendaViewModel) {

    val state by viewModel.uiState.collectAsState()

    when (val s = state) {
        is TiendaUiState.Loading -> {
            // TODO Mostrar pantalla de carga
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Cargando tienda...")
            }
        }
        is TiendaUiState.Success -> {
            // TODO Mostrar la tienda con los productos reales
            TiendaContent(
                sep = s.saldo,
                productos = s.productos,
                onComprarProducto = { producto ->
                    viewModel.comprarProducto(producto)
                }
            )
        }
        is TiendaUiState.Error -> {
            // TODO Mostrar mensaje de error
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error al cargar la tienda: ${s.message}")
            }
        }
    }


}

@Composable
fun TiendaContent(
    sep: Int,
    productos: List<Producto>,
    onComprarProducto: (Producto) -> Unit
) {

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Crea mapa "Cat 1" -> 0, "Cat 2" -> 8, ... (indice de inicio de cada categoria o numero de productos)
    val categorias = remember(productos) { productos.map { it.categoria }.distinct() }
    var categoriaSeleccionada by remember { mutableStateOf(categorias.firstOrNull() ?: "") }
    // FIXME no necesario segun el diseño planteado pero se puede mirar
    //var productoSeleccionado by remember { mutableStateOf<Producto?>(null) }

    // Sincronizar scroll y botones de categoria
    LaunchedEffect(listState.firstVisibleItemIndex) {
        // Obtener el producto visible actualmente
        val productoVisible = productos.getOrNull(listState.firstVisibleItemIndex)

        // Si el producto visible pertenece a una categoria diferente a la seleccionada, actualizar la seleccion
        productoVisible?.let {
            if (categoriaSeleccionada != it.categoria) {
                categoriaSeleccionada = it.categoria
            }
        }
    }

    var productoSeleccionado by remember { mutableStateOf<Producto?>(null) }

    productoSeleccionado?.let { producto ->
        // Mostrar detalle del producto en un Dialog
        DetalleProductoTienda(
            producto = producto,
            onDismiss = { productoSeleccionado = null },
            onComprar = { prod ->
                onComprarProducto(producto)
                productoSeleccionado = null
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color_bg)
            .padding(top = 16.dp)
    ) {

        // CABECERA
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Tienda",
                style = SETextTypes.titulo,
                color = color_text
            )

            Text(
                text = "Sep $sep", // TODO Reemplazar con el saldo real del usuario
                style = SETextTypes.SEPStyle,
                color = color_SEPText,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp)
                    .background(color_fondoTienda, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            )
        }

        // CUERPO TIENDA
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .clip(RoundedCornerShape(24.dp))
                .border(2.dp, color_sf, RoundedCornerShape(24.dp))
                .background(color_fondoTienda)
        ) {
            // BOTONES DE CATEGORIAS
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color_fondoTienda)
            ) {
                categorias.forEach { categoria ->
                    BotonCategoriaCustom(
                        titulo = categoria,
                        estaSeleccionado = categoriaSeleccionada == categoria,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            val index = productos.indexOfFirst { it.categoria == categoria }
                            if (index != -1) {
                                coroutineScope.launch {
                                    listState.animateScrollToItem(index)
                                }
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Lista de productos
            Box (
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LazyRow(
                    state = listState,
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(productos) { producto ->
                        TarjetaProductoTienda(
                            producto = producto,
                            onClick = {
                                productoSeleccionado = producto
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
