package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.NavHost
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.MenuTopBar
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.*
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SerpientesYEscalerasReMixTheme

// MainActivity, muestra topBar y contenido de la pantalla en base a la navegación

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Esta línea oculta la barra de soporte
            actionBar?.hide()

            // Estado de la pantalla a mostrar
            val SEState = rememberSEAppState()

            SerpientesYEscalerasReMixTheme {
                Scaffold(
                    topBar = { MenuTopBar(SEState) },
                    content = {
                        NavHost( // Componente en el que se iran dibujando las pantallas de acuerdo a la navegación
                            navController = SEState.navController,
                            startDestination = Destinos.JUGAR,
                        ) {
                            // Inclusión de los diferentes grafos de navegación
                            menuTopBarGraph()
                        }

                    }
                )
            }
        }
    }
}

