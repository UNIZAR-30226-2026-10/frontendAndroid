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
    private val numRegex = ".*[0-9].*".toRegex()
    private val upperRegex = ".*[A-Z].*".toRegex()
    private val lowerRegex = ".*[a-z].*".toRegex()
    private val specialRegex = ".*[!@#$%^&*(),.?\":{}|<>].*".toRegex()

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
        val email = _uiState.value.email
        val username = _uiState.value.username
        val passwd = _uiState.value.password
        val confirmPasswd = _uiState.value.confirmPassword

        if (!email.matches(emailRegex)) {
            showErrorSnackbar("Incluye un signo \"@\" en la dirección de correo electrónico. La dirección \"${uiState.value.email}\" no incluye el signo \"@\"")
        } else if (username.isBlank()) {
            showErrorSnackbar("El nombre es obligatorio")
        } else if (passwd.length < 8) {
            showErrorSnackbar("La contraseña debe tener al menos 8 caracteres")
        } else if (passwd.length > 128) {
            showErrorSnackbar("La contraseña no puede tener más de 128 caracteres")
        } else if (!passwd.contains(numRegex)) {
            showErrorSnackbar("La contraseña debe contener al menos un número")
        } else if (!passwd.contains(upperRegex)) {
            showErrorSnackbar("La contraseña debe contener al menos una letra mayúscula")
        } else if (!passwd.contains(lowerRegex)) {
            showErrorSnackbar("La contraseña debe contener al menos una letra minúscula")
        } else if (!passwd.contains(specialRegex)) {
            showErrorSnackbar("La contraseña debe contener al menos un carácter especial")
        } else if (passwd != confirmPasswd) {
            showErrorSnackbar("Las contraseñas no coinciden")
        } else{
            viewModelScope.launch {
                val success =
                    cF.registrarseCase(username, email, passwd)
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