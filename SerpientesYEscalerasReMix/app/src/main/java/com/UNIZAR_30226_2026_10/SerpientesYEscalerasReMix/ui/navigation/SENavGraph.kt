package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CaseFacade
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.MazosScreen
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Jugar_Amigos.AmigosScreen
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Jugar_Amigos.AmigosViewModel
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Jugar_Continuar.JugarContinuarScreen
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Jugar_Continuar.JugarContinuarViewModel
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Jugar_Crear.JugarCrearScreen
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Jugar_Crear.JugarCrearViewModel
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Login.LoginScreen
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.LogrosScreen
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Partida.PartidaScreen
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Perfil
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Register.RegisterScreen
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.TiendaScreen

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
    const val PARTIDA = "partida"
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
        val jugarCrearViewModel: JugarCrearViewModel = viewModel(
            factory = JugarCrearViewModel.Factory(cF)
        )
        JugarCrearScreen(SEState, jugarCrearViewModel)
    }

    composable(Destinos.JUGAR_CONTINUAR,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(700)
            )
        }
    ) {
        val jugarContinuarViewModel: JugarContinuarViewModel = viewModel(
            factory = JugarContinuarViewModel.Factory(cF)
        )
        JugarContinuarScreen(SEState, jugarContinuarViewModel)
    }

    composable(
        Destinos.JUGAR_AMIGOS,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(700)
            )
        }
    ) {
        val amigosViewModel: AmigosViewModel = viewModel(
            factory = AmigosViewModel.Factory(cF)
        )
        AmigosScreen(SEState, snackHost, amigosViewModel)
    }

    composable(Destinos.MAZOS){
        MazosScreen(SEState)
    }

    composable(Destinos.LOGROS) {
        LogrosScreen(SEState)
    }

    composable(Destinos.TIENDA) {
        TiendaScreen(SEState)
    }

    composable(Destinos.PERFIL) {
        Perfil(SEState)
    }

    composable(Destinos.PARTIDA) {
        PartidaScreen(SEState)
    }
}
