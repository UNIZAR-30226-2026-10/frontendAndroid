package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Login

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CaseFacade
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(private val cF: CaseFacade, private val snackHost: SnackbarHostState) : ViewModel() {

    // CONSTRUCCIÓN

    companion object {
        fun Factory(cF: CaseFacade, snackHost: SnackbarHostState): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T = LoginViewModel(cF, snackHost) as T
        }
    }

    // ESTADO

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    // Expresión regular para validar email
    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-z]+\$".toRegex()


    // FUNCIONES

    private fun showErrorSnackbar(message: String) {
        viewModelScope.launch {
            snackHost.showSnackbar(message)
        }
    }

    fun onEmailChange(newValue: String) {
        _uiState.update { it.copy(email = newValue) }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(password = newValue) }
    }

    fun login(onSuccess: () -> Unit) {
        val currentEmail = _uiState.value.email
        val currentPassword = _uiState.value.password

        if (!currentEmail.matches(emailRegex)) {
            showErrorSnackbar("Incluye un signo \"@\" en la dirección de correo electrónico. La dirección \"${uiState.value.email}\" no incluye el signo \"@\"")
        } else if (currentPassword.isBlank()) {
            showErrorSnackbar("La contraseña no puede estar vacía")
        } else {
            viewModelScope.launch {
                val success = cF.loginRegisterCase.iniciarSesion(currentEmail, currentPassword)
                if (success) {
                    onSuccess()
                } else {
                    showErrorSnackbar("Usuario o contraseña incorrectos")
                }
            }
        }
    }
}

data class LoginUiState(
    val email: String = "",
    val password: String = ""
)