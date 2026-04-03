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
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.fijarOrientacion
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.Destinos
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.prepararOrientacion
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes.seleccionable
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes.subtitulo
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes.titulo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(SEState: SENavHostController, snackHost: SnackbarHostState, cF: CaseFacade) {
    // Para poder ejecutar corutines
    val scope = rememberCoroutineScope()

    // Ver la pantalla en vertical
    fijarOrientacion(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    // Variables en las que ir guardando el texto introducido
    var emailForm by remember { mutableStateOf("") }
    var passwordForm by remember { mutableStateOf("") }

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

            Text("Iniciar Sesión", style = subtitulo)

            Spacer(Modifier.height(40.dp))

            // Campo de Usuario (e-mail)
            OutlinedTextField(
                value = emailForm,
                onValueChange = { emailForm = it },
                label = { Text("Usuario") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(16.dp))

            // Campo de Contraseña
            OutlinedTextField(
                value = passwordForm,
                onValueChange = { passwordForm = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                // Ocultar los caracteres de la contraseña
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { loginButtonAction(emailForm, passwordForm, scope, snackHost, SEState, cF) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("INICIAR SESIÓN")
            }

            TextButton(onClick = { registerButtonAction(SEState) }) {
                Text(text = "¿No tienes cuenta? Regístrate aquí", style = seleccionable)
            }
        }
    }
}

fun loginButtonAction(
    emailForm: String, passwordForm: String, scope: CoroutineScope,
    snackHost: SnackbarHostState, SEState: SENavHostController, cF: CaseFacade
) {
    scope.launch {
        val invalidForms = emailForm == "" || passwordForm == ""
        val loginSuccess = !invalidForms && cF.loginRegisterCase.iniciarSesion(emailForm, passwordForm)
        if (loginSuccess) {
            // Preparar la orientación en horizontal
            prepararOrientacion(SEState, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

            SEState.goTo(Destinos.JUGAR_CREAR)
        } else {
            snackHost.showSnackbar(
                message = "Usuario o Contraseña incorrectos, porfavor Introduzcalos de nuevo.",
                duration = SnackbarDuration.Short
            )
        }
        print("hola")
    }
}

fun registerButtonAction(SEState: SENavHostController) {
    // Preparar la orientación en vertical
    prepararOrientacion(SEState, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    SEState.goTo(Destinos.REGISTER)
}
