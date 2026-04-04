package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.local.LocalStorage
import kotlinx.coroutines.flow.MutableStateFlow

class LoginRegisterCase(
    private val local: LocalStorage,
    private val email: MutableStateFlow<String>,
    private val username: MutableStateFlow<String>
) { // TODO añadir remote login

    suspend fun comprobarLogin(): String {
        if (local.getLogin()) {
            email.value = local.getEmail()
            val passwd = local.getPasswd()
            iniciarSesion(email.value, passwd)
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
        local.clearLogin()
        email.value = ""
        username.value = ""
    }

    suspend fun registrarse(_username: String, _email: String, _passwd: String): Boolean {

        // TODO Llamada a la API

        if (true) { // TODO Cambiar: if no error en llamada a la API
            local.setLogin(true)
            local.setEmail(_email)
            local.setPasswd(_passwd)
            email.value = _email
            username.value = _username

            return true
        } else {
            return false
        }
    }
}