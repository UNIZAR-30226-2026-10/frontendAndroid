package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation

import android.util.Log
import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.JugarCrearScreen

// clase objeto utilizada como un enum. Define los destinos usados en los grafos de navegación
object Destinos {
    const val JUGAR_CREAR = "crearPartida"
    const val JUGAR_CONTINUAR = "continuarPartida"

    const val MAZOS = "mazos"
    const val LOGROS = "logros"
    const val TIENDA = "tienda"
    const val PERFIL = "perfil"
}

// Función que encapsula la navegación del menu superior de la aplicación.
//      Extiende a NavGraphBuilder para poder llamarse dentro de un NavHost
fun NavGraphBuilder.menuTopBarGraph(SEState : SENavHostController) {
    composable(Destinos.JUGAR_CREAR) {
        JugarCrearScreen(SEState)
    }

    composable(Destinos.JUGAR_CONTINUAR) {
        Text("...")
    }

    composable(Destinos.MAZOS) {
        Text("Pantalla de Mazos")
    }

    composable(Destinos.LOGROS) {
        Text("Pantalla de Logros")
    }

    composable(Destinos.TIENDA) {
        Text("Tienda de items")
    }

    composable(Destinos.PERFIL) {
        Text("Perfil del Jugador")
    }
}
