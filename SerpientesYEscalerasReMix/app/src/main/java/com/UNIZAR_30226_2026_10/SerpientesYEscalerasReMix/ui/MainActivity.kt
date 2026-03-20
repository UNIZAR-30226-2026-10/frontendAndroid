package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.NavHost
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CaseFacade
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.MenuTopBar
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.Destinos
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.navGraph
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.rememberSEAppState
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SerpientesYEscalerasReMixTheme
import kotlinx.coroutines.runBlocking

// MainActivity, muestra topBar y contenido de la pantalla en base a la navegación

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialización de los casos de uso
        val caseFacade = CaseFacade(applicationContext)

        setContent {
            // Esta línea oculta la barra de soporte
            actionBar?.hide()

            // Ocultar menu superior e inferior de Android (apareceran deslizando el dedo)
            WindowCompat.setDecorFitsSystemWindows(window, false)
            val controller = WindowInsetsControllerCompat(window, window.decorView)
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

            // Dibujar los elementos de la app
            mainScreen(caseFacade)
        }
    }
}

@Composable
fun mainScreen(cF: CaseFacade) {
    // Estado de la pantalla a mostrar
    val SEState = rememberSEAppState()

    val email = runBlocking {
        cF.loginRegisterCase.comprobarLogin()
    }

    // Pantalla incial en función del login
    val pantallaIni = if (email == "") Destinos.LOGIN
    else Destinos.JUGAR_CREAR

    SerpientesYEscalerasReMixTheme {
        Scaffold(
            topBar = { MenuTopBar(SEState) },
            content = { padding ->
                NavHost(
                    // Componente en el que se iran dibujando las pantallas de acuerdo a la navegación
                    navController = SEState.navController,
                    startDestination = pantallaIni,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    // Inclusión de los diferentes grafos de navegación
                    navGraph(SEState)
                }
            }
        )
    }
}

@Preview(
    device = "spec:width=891dp,height=411dp,orientation=landscape,dpi=440"
)
@Composable
fun mainScreenDebug() {
    // Estado de la pantalla a mostrar
    val SEState = rememberSEAppState()

    // Pantalla incial en función del login

    SerpientesYEscalerasReMixTheme {
        Scaffold(
            topBar = { MenuTopBar(SEState) },
            content = { padding ->
                NavHost(
                    // Componente en el que se iran dibujando las pantallas de acuerdo a la navegación
                    navController = SEState.navController,
                    startDestination = Destinos.JUGAR_CREAR,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    // Inclusión de los diferentes grafos de navegación
                    navGraph(SEState)
                }
            }
        )
    }
}