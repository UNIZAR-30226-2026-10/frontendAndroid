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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.BotonCategoriaCustom
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.TarjetaProductoTienda
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Tipo_Producto
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_SEPText
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_sf
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_text
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fondoTienda
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.DetalleProductoTienda
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Tienda.TiendaViewModel
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.BotonGenerico
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_online
import androidx.compose.ui.graphics.Color


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
    // Truquillo de ordenacion para que se 1.Escalera 2.Icono 3.Serpiente 4.Ficha
    // TODO AÑADIR MAS FICHAS A LA BD PARA VER MEJOR EL EFECTO DE LA ORDENACION, YA QUE SI HAY MENOS DE 5 NO SE VE
    val categorias = remember(productos) {
        productos.map { it.tipo }
            .distinct()
            .sortedWith(compareBy { tipo ->
                when (tipo) {
                    Tipo_Producto.Ficha -> 4
                    Tipo_Producto.Serpiente -> 3
                    Tipo_Producto.Escalera -> 2
                    Tipo_Producto.Icono -> 1
                    else -> 0
                }
            })
    }
    val productosOrdenados = remember(productos, categorias) {
        productos.sortedWith(compareBy { producto ->
            categorias.indexOf(producto.tipo)
        })
    }
    var categoriaSeleccionada by remember { mutableStateOf(categorias.firstOrNull() ?: "") }
    // FIXME no necesario segun el diseño planteado pero se puede mirar
    //var productoSeleccionado by remember { mutableStateOf<Producto?>(null) }

    // Sincronizar scroll y botones de categoria
    LaunchedEffect(listState.firstVisibleItemIndex) {
        // Obtener el producto visible actualmente
        val productoVisible = productosOrdenados.getOrNull(listState.firstVisibleItemIndex)

        // Si el producto visible pertenece a una categoria diferente a la seleccionada, actualizar la seleccion
        productoVisible?.let {
            if (categoriaSeleccionada != it.tipo) {
                categoriaSeleccionada = it.tipo
            }
        }
    }

    var productoSeleccionado by remember { mutableStateOf<Producto?>(null) }
    var avisoProductoEnPosesion by remember { mutableStateOf(false) }

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

    if (avisoProductoEnPosesion) {
        androidx.compose.ui.window.Dialog(
            onDismissRequest = { avisoProductoEnPosesion = false }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .background(color_bg, RoundedCornerShape(12.dp))
                    .border(2.dp, color_sf, RoundedCornerShape(12.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Cosmetico ya en posesión",
                        style = SETextTypes.plano,
                        color = color_text
                    )
                    BotonGenerico(
                        texto = "Cerrar",
                        onClick = { avisoProductoEnPosesion = false },
                        modifier = Modifier.padding(top = 12.dp),
                        colorPrincipal = color_online,
                        habilitado = true
                    )
                }
            }
        }
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
                text = "Sep $sep",
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
            // FIXME hacer que se muestren lo ultimo las fichas para que se vea que se ha seleccionado la categoria, ya que es la que mas ocupa
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color_fondoTienda)
            ) {

                categorias.forEach { categoria ->
                    //Log.d("TiendaContent", "Hasta aqui llega con categoria: $categoria")
                    BotonCategoriaCustom(
                        titulo = categoria.toString(),
                        estaSeleccionado = categoriaSeleccionada == categoria,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            val index = productosOrdenados.indexOfFirst { it.tipo == categoria }
                            if (index != -1) {
                                coroutineScope.launch {
                                    listState.animateScrollToItem(index)
                                }
                            }
                        }
                    )
                    //Log.d("TiendaContent", "Despues del boton con categoria: $categoria")
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
                    items(productosOrdenados) { producto ->
                        TarjetaProductoTienda(
                            producto = producto,
                            onClick = {
                                if (producto.enPosesion) {
                                    avisoProductoEnPosesion = true
                                } else {
                                    productoSeleccionado = producto
                                }
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
