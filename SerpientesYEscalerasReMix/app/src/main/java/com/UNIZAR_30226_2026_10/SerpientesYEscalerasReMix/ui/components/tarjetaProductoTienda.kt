package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_sf
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Producto

@Composable
fun TarjetaProductoTienda(
    producto: Producto,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(160.dp)
            .clickable { onClick() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .border(1.dp, color_sf, shape = RoundedCornerShape(8.dp)),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column {
                Text(
                    text = producto.nombreAMostrar,
                    style = SETextTypes.plano, //TODO
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally)
                )
                // Prueba carga con Coil y url
                /*AsyncImage(
                    model = producto.imagenUrl,
                    contentDescription = producto.descripcion,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    placeholder = painterResource(R.drawable.ic_launcher_foreground), // Reemplaza con tu recurso de placeholder

                    error = painterResource(R.drawable.ic_launcher_foreground), // Reemplaza con tu recurso de error
                )*/
                Image(
                    painter = painterResource(producto.imageResId ?: R.drawable.corona),
                    contentDescription = producto.nombreAMostrar,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )

                Text(
                    text = "${producto.precio} Sep",
                    style = SETextTypes.SEPStyle,
                    // Centrar el texto horizontalmente dentro de la columna
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }

        if (producto.enPosesion) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x99000000))
            )
        }
    }

}
