package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CaseFacade
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.AmigosScreen
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.JugarContinuarScreen
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.JugarCrearScreen
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.LoginScreen
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Perfil
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.TiendaScreen
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.RegisterScreen

// clase objeto utilizada como un enum. Define los destinos usados en los grafos de navegación
object Destinos {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val JUGAR_CREAR = "crearPartida"
    const val JUGAR_CONTINUAR = "continuarPartida"
    const val JUGAR_AMIGOS = "amigos"
    const val MAZOS = "mazos"
    const val LOGROS = "logros"
    const val TIENDA = "tienda"

    const val PERFIL = "perfil"
}

// Función que encapsula la navegación del menu superior de la aplicación.
//      Extiende a NavGraphBuilder para poder llamarse dentro de un NavHost
fun NavGraphBuilder.navGraph(SEState: SENavHostController, snackHost: SnackbarHostState, cF: CaseFacade) {
    composable(Destinos.LOGIN) {
        LoginScreen(SEState, snackHost, cF)
    }

    composable(Destinos.REGISTER) {
        RegisterScreen(SEState, snackHost, cF)
    }

    composable(Destinos.JUGAR_CREAR) {
        JugarCrearScreen(SEState)
    }

    composable(Destinos.JUGAR_CONTINUAR) {
        JugarContinuarScreen(SEState)
    }

    composable(
        Destinos.JUGAR_AMIGOS,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(700)
            )
        }
    ) {
        AmigosScreen(SEState)
    }

    composable(Destinos.MAZOS) {
        Text("Pantalla de Mazos")
    }

    composable(Destinos.LOGROS) {
        Text("Pantalla de Logros")
    }

    composable(Destinos.TIENDA,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(700)
            ) }
    ) {
        TiendaScreen(SEState)
    }

    composable(Destinos.PERFIL) {
        Perfil(SEState)
    }
}
