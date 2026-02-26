package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

// NOTA: Ejemplo para comprobar que el entorno funcina (generado por Gemini)
// A borrar y diseñar aqui la main activity luego

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Un contenedor básico que usa el tema del sistema
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingScreen()
                }
            }
        }
    }
}

@Preview
@Composable
fun GreetingScreen() {
    // Estado simple para demostrar que Compose reacciona a clics
    var clicks by rememberSaveable { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¡Serpientes y Escaleras ReMix!",
            fontSize = 30.sp,
            style = MaterialTheme.typography.headlineLarge
        )

        Text(text = "Has presionado el botón $clicks veces")

        Button(onClick = { clicks++ }) {
            Text("Lanzar Dado 🎲")
        }
    }
}
