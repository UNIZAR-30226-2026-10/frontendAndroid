package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.NavHost
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.local.LocalStorage
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.ApiClient
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.repository.ConexionRepositoryImpl
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.repository.LoginRegisterRepositoryImpl
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.repository.PartidaRepositoryImpl
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

        // Inicialización de data.remote, Retrofit
        ApiClient.init(applicationContext)
        val apiService = ApiClient.apiService

        // Inicialización de data.local
        val localStorage = LocalStorage(applicationContext)

        // Inicialización de los casos de uso
        val caseFacade = CaseFacade(
            pruebaConexionRepository =  ConexionRepositoryImpl(apiService),
            loginRegisterRepository = LoginRegisterRepositoryImpl(apiService, localStorage),
            partidaRepository =  PartidaRepositoryImpl()
        )

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
            MainScreen(caseFacade)
        }
    }
}


@Composable
fun MainScreen(cF: CaseFacade) {
    // Estado de la pantalla a mostrar
    val SEState = rememberSEAppState()

    val email = runBlocking {
        cF.comprobarLoginCase.invoke()
    }

    // Prueba de Conectividad Logging/Debug
    LaunchedEffect(Unit) {
        val isConnected = cF.pruebaConexionCase()
        if (isConnected) {
            Log.d("RETROFIT_TEST", "✅ Conexión exitosa y GSON configurado")
        } else {
            Log.e("RETROFIT_TEST", "❌ Fallo de conexión al dominio")
        }
    }

    // Pantalla incial en función del login
    val pantallaIni = if (email == "") Destinos.LOGIN
    else Destinos.JUGAR_CREAR

    // Para mostrar PopUps en las pantallas
    val snackbarHostState = remember { SnackbarHostState() }

    SerpientesYEscalerasReMixTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState, modifier = Modifier.padding(20.dp)) },
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
                    navGraph(SEState, snackbarHostState, cF)
                }
            }
        )
    }
}
