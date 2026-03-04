package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

// Clase encargada de realizar la navegación entre pantallas de la aplicación:
//      Dibuja pantallas en objetos del tipo NavHost
//      Guarda pila con el historial de pantallas
class SENavHostController( val navController: NavHostController ) {
    fun goTo(ruta: String) { // Evita pantallas repitadas en la pila
        val idInicio = navController.graph.findStartDestination().id

        navController.navigate(ruta) {
            // Usamos el ID encontrado de forma segura
            popUpTo(idInicio) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    // Función que devuelve en que tab nos encontramos:
    //      Destacar sección en menu superior
    //      Cambiar a menu superior de partidas
    @Composable
    fun rutaActual(): String? {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        return navBackStackEntry?.destination?.route
    }
}

/**
 * Función para recordar el estado entre recomposiciones.
 */
@Composable
fun rememberSEAppState(
    navController: NavHostController = rememberNavController()
): SENavHostController = remember(navController) {
    SENavHostController(navController)
}
