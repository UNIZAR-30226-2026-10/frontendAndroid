package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Login

import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.fijarOrientacion
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.Destinos
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.prepararOrientacion
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes.seleccionable
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes.subtitulo
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes.titulo
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_secondary

@Composable
fun LoginScreen(
    navController: SENavHostController,
    viewModel: LoginViewModel = viewModel()
) {
    // Estado observable de la pantalla
    val uiState by viewModel.uiState.collectAsState()

    // Ver la pantalla en vertical
    fijarOrientacion(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("S&E REMIX", style = titulo)

            Spacer(Modifier.height(10.dp))

            Text("¡Bienvenido! Inicia Sesión", style = subtitulo)

            Spacer(Modifier.height(40.dp))

            // Campo de Usuario (e-mail)
            OutlinedTextField(
                value = uiState.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(16.dp))

            // Campo de Contraseña
            OutlinedTextField(
                value = uiState.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                // Ocultar los caracteres de la contraseña
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { viewModel.login(
                    {
                        // Preparar la orientación en horizontal y navegar
                        prepararOrientacion(navController, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
                        navController.goTo(Destinos.JUGAR_CREAR)
                    }
                ) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(color_secondary),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("INICIAR SESIÓN")
            }

            TextButton(
                onClick = {
                    // Preparar la orientación en vertical y navegar
                    prepararOrientacion(navController, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                    navController.goTo(Destinos.REGISTER)
                }
            ) {
                Text(text = "¿No tienes cuenta? Registrate", style = seleccionable)
            }
        }
    }
}
