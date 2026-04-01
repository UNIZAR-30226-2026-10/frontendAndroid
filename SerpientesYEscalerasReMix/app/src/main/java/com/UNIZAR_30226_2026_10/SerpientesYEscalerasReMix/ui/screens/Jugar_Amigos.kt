package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CaseFacade
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.CabeceraAmigos
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.ListaAmigos
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController

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
fun AmigosScreen(SEState: SENavHostController, cF: CaseFacade) {

    val listaAmigosOriginal = remember { listaAmigosEjemplo }

    var listaAmigosBuscada by remember { mutableStateOf(emptyList<Amigo>()) }

    val listaAmigosMostrada = remember(listaAmigosBuscada) {
        if (listaAmigosBuscada.isEmpty()) {
            listaAmigosOriginal
        } else {
            listaAmigosBuscada
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cabecera, buscar amigos y volver a la pantalla anterior
        CabeceraAmigos(
            SEState,
            cF,
            onBusquedaFinalizada = { nuevaLista -> listaAmigosBuscada = nuevaLista }
        )

        ListaAmigos(listaAmigosMostrada)
    }
}
