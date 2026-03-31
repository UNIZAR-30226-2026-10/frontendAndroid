package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CaseFacade
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.LockScreenOrientation
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController

@Composable
fun LoginScreen(SEState: SENavHostController, cF: CaseFacade) {
    // Ver la pantalla en vertical
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

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
            Text("S&E REMIX", fontSize = 36.sp, fontWeight = FontWeight.Bold)
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
                onClick = { /* Login */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("INICIAR SESIÓN")
            }

            TextButton(onClick = {}) {
                Text("¿No tienes cuenta? Regístrate aquí")
            }
        }
    }
}

@Preview
@Composable
fun LoginContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("S&E REMIX", fontSize = 36.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(40.dp))

            Spacer(Modifier.height(16.dp))

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { /* Login */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("INICIAR SESIÓN")
            }

            TextButton(onClick = {}) {
                Text("¿No tienes cuenta? Regístrate aquí")
            }
        }
    }
}

