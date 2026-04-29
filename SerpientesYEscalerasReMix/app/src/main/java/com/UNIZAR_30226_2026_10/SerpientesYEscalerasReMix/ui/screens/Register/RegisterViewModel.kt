package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Register

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CaseFacade
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
class RegisterViewModel(private val cF: CaseFacade, private val snackHost: SnackbarHostState) : ViewModel() {

    // CONSTRUCCIÓN

    companion object {
        fun Factory(cF: CaseFacade, snackHost: SnackbarHostState): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T = RegisterViewModel(cF, snackHost) as T
            }
    }

    // ESTADO

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

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

    fun onUsernameChange(newValue: String) {
        _uiState.update { it.copy(username = newValue) }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(password = newValue) }
    }

    fun onConfirmPasswordChange(newValue: String) {
        _uiState.update { it.copy(confirmPassword = newValue) }
    }

    fun register(onSuccess: () -> Unit) {
        val state = _uiState.value

        if (!state.email.matches(emailRegex)) {
            showErrorSnackbar("Incluye un signo \"@\" en la dirección de correo electrónico. La dirección \"${uiState.value.email}\" no incluye el signo \"@\"")
        } else if (state.password != state.confirmPassword) {
            showErrorSnackbar("Las contraseñas no coinciden")
        } else if (state.username.isBlank()) {
            showErrorSnackbar("El nombre es obligatorio")
        } else {
            viewModelScope.launch {
                val success =
                    cF.loginRegisterCase.registrarse(state.username, state.email, state.password)
                if (success) {
                    onSuccess()
                } else {
                    showErrorSnackbar("Error al crear la cuenta")
                }
            }
        }
    }
}

data class RegisterUiState(
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)