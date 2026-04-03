package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.local.LocalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginRegisterCase(private val local: LocalStorage) { // TODO añadir remote login

    private val email = MutableStateFlow("")
    private val username = MutableStateFlow("")
    val emailFlow: StateFlow<String> = email.asStateFlow() // Observable desde otros usecase
    val usernameFlow: StateFlow<String> = username.asStateFlow() // Observable desde otros usecase

    suspend fun comprobarLogin(): String {
        if (local.getLogin()) {
            email.value = local.getEmail()
            return email.value
        } else {
            return ""
        }
    }

    suspend fun iniciarSesion(_email: String, passwd: String): Boolean {

        // TODO Llamada a la API

        if (true) { // TODO Cambiar: if no error en llamada a la API
            local.setLogin(true)
            local.setEmail(_email)
            email.value = _email
            username.value = "Yo" // TODO Cambiar con lo que devuelva la API

            return true
        } else {
            return false
        }
    }

    suspend fun cerrarSesion() {
        local.clearEmail()
        email.value = ""
        username.value = ""
    }

    suspend fun registrarse(_username: String, _email: String, passwd: String): Boolean {

        // TODO Llamada a la API

        if (true) { // TODO Cambiar: if no error en llamada a la API
            local.setLogin(true)
            local.setEmail(_email)
            email.value = _email
            username.value = _username

            return true
        } else {
            return false
        }
    }
}