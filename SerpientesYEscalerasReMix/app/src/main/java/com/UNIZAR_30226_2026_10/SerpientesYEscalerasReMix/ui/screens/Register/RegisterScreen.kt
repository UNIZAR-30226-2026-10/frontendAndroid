package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Register

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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CaseFacade
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.LockScreenOrientation
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.Destinos
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.prepareScreenOrientation
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes.seleccionable
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes.subtitulo
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes.titulo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(SEState: SENavHostController, snackHost: SnackbarHostState, cF: CaseFacade) {
    val scope = rememberCoroutineScope()

    // Mantenemos el modo vertical para el formulario
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    var emailForm by remember { mutableStateOf("") }
    var passwordForm by remember { mutableStateOf("") }
    var confirmPasswordForm by remember { mutableStateOf("") }

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

            Text("Registrarse", style = subtitulo)

            Spacer(Modifier.height(40.dp))

            // Campo Usuario
            OutlinedTextField(
                value = emailForm,
                onValueChange = { emailForm = it },
                label = { Text("Usuario / Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(16.dp))

            // Campo Contraseña
            OutlinedTextField(
                value = passwordForm,
                onValueChange = { passwordForm = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(Modifier.height(16.dp))

            // Campo Confirmar Contraseña
            OutlinedTextField(
                value = confirmPasswordForm,
                onValueChange = { confirmPasswordForm = it },
                label = { Text("Confirmar Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    if (passwordForm == confirmPasswordForm) {
                        registerButtonAction(emailForm, passwordForm, confirmPasswordForm, scope, snackHost, SEState, cF)
                    } else {
                        // Aquí podrías mostrar un Toast de "Las contraseñas no coinciden"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("REGISTRARSE")
            }

            TextButton(onClick = { SEState.goTo(Destinos.LOGIN) }) {
                Text(text = "¿Ya tienes cuenta? Inicia sesión", style = seleccionable)
            }
        }
    }
}

fun registerButtonAction(
    emailForm: String, passwordForm: String, confirmPasswordForm: String,
    scope: CoroutineScope, snackHost: SnackbarHostState, SEState: SENavHostController, cF: CaseFacade
) {
    scope.launch {
        val invalidForms = emailForm == "" || passwordForm == "" || passwordForm != confirmPasswordForm
        val registerSuccess = !invalidForms && cF.loginRegisterCase.registrarse(emailForm, passwordForm)
        if (registerSuccess) {
            // Preparar el cambio a horizontal para el juego
            prepareScreenOrientation(SEState, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

            SEState.goTo(Destinos.JUGAR_CREAR)
        } else {
            snackHost.showSnackbar(
                message = "Usuario o Contraseña incorrectos, porfavor Introduzcalos de nuevo.",
                duration = SnackbarDuration.Short
            )
        }
    }
}