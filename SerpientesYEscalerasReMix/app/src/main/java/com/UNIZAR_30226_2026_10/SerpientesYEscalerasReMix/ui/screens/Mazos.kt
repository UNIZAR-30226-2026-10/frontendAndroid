package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.fakes.listaDeMazosDePrueba
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.BotonCategoriaCustom
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_bg
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_fondoTienda
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_sf
import androidx.compose.material3.Text
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.fakes.mazoVacio
import androidx.compose.foundation.lazy.grid.items
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.BotonEditarMazo
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.BotonGenerico
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.BotonNuevoMazo
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.BotonEliminarMazo
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_offline
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_online

@Composable
fun MazosScreen(SEState: SENavHostController){

    //FIXME mazos temporal hasta que se implemente el ViewModel y se conecte con la base de datos
    val mazos = listaDeMazosDePrueba

    var mazoSeleccionado by remember { mutableStateOf(mazos.firstOrNull()) }

    if (mazoSeleccionado == null) { mazoSeleccionado = mazoVacio }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color_bg)
            .padding(16.dp)
    ){
        // PANEL DE MAZOS
        Column(
            modifier = Modifier
                .weight(0.7f)
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .clip(RoundedCornerShape(24.dp))
                .border(2.dp, color_sf, RoundedCornerShape(24.dp))
                .background(color_fondoTienda)
            //horizontalAlignment = Alignment.Start
        ) {
            // BOTONES POR CADA MAZO (MAX 8)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color_fondoTienda)
            ) {
                mazos.forEachIndexed { index, mazo ->
                    BotonCategoriaCustom(
                        titulo = "Mazo ${index + 1}",
                        estaSeleccionado = mazoSeleccionado == mazo,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            // TODO cambiar de mazo seleccionado
                            mazoSeleccionado = mazo
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Contenido del mazo seleccionado
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp)
            ) {
                Text(
                    text = mazoSeleccionado?.nombre ?: "Mazo 1",
                    style = SETextTypes.nombreMazo,
                    color = color_text,
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // TODO mostrar cartas del mazo seleccionado
                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    userScrollEnabled = false,
                    contentPadding = PaddingValues(4.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(50.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    items(mazoSeleccionado!!.cartas) { carta ->
                        // TODO mostrar carta
                        Box (
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(0.7f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(color_offline, RoundedCornerShape(4.dp))
                                .border(1.dp, color_sf.copy(alpha = 0.5f), RoundedCornerShape(4.dp)),
                        )
                    }
                }
            }
        }

        // PANEL DE ACCIONES DEL MAZO SELECCIONADO
        Column(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // BOTON PARA CREAR NUEVO MAZO
            BotonNuevoMazo(
                onClick = {
                    // TODO crear nuevo mazo
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            // BOTON PARA EDITAR MAZO SELECCIONADO
            BotonEditarMazo(
                onClick = {
                    // TODO editar mazo seleccionado
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )


            // BOTON PARA ELIMINAR MAZO SELECCIONADO
            BotonEliminarMazo(
                onClick = {
                    // TODO eliminar mazo seleccionado
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }

    }
}