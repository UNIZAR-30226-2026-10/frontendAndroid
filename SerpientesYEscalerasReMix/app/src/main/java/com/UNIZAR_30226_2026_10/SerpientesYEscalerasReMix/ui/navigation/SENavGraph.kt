package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.JugarCrearScreen

// clase objeto utilizada como un enum. Define los destinos usados en los grafos de navegación
object Destinos {
    const val JUGAR_BASE = "jugar"
    const val JUGAR = "$JUGAR_BASE?modo={modo}"

    const val MAZOS = "mazos"
    const val LOGROS = "logros"
    const val TIENDA = "tienda"
    const val PERFIL = "perfil"
}

// Función que encapsula la navegación del menu superior de la aplicación.
//      Extiende a NavGraphBuilder para poder llamarse dentro de un NavHost
fun NavGraphBuilder.menuTopBarGraph(SEState : SENavHostController) {
    composable(
        route = Destinos.JUGAR,
        arguments = listOf(
            navArgument("modo") {
                nullable = true
                defaultValue = "Crear"
            }
        )
    ) { backStackEntry ->
        // Extraer el valor del argumento
        val modo = backStackEntry.arguments?.getString("modo")

        if (modo == "Crear") {
            JugarCrearScreen(SEState)
        } else if (modo == "Continuar"){
            Text("...")
        }
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
