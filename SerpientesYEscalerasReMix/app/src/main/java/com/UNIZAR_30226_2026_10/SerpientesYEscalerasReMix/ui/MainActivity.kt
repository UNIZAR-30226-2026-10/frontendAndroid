package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components.MenuTopBar
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SerpientesYEscalerasReMixTheme

// MainActivity, muestra topBar y contenido de la pantalla en base a la navegación

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Estado de la pantalla a mostrar
            var pantallaActual by remember { mutableStateOf("Jugar") }

            SerpientesYEscalerasReMixTheme {
                Scaffold(
                    topBar = { MenuTopBar() }, // pasar pantalla actial para cambiar el aspecto del tab
                    content = {
                        when (pantallaActual) {
                            "Jugar" -> Text("por implementar")
                            "Mazos" -> Text("por implementar")
                            "Logros" -> Text("por implementar")
                            "Tienda" -> Text("por implementar")
                            "Perfil" -> Text("por implementar")
                            "Partida" -> Text("por implementar")
                        }
                )
            }
        }
    }
}

