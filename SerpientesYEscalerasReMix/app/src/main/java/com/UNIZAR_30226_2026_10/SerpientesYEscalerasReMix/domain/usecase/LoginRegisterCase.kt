package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.local.LocalStorage

class LoginRegisterCase(private val local: LocalStorage) { // TODO añadir remote login

    suspend fun comprobarLogin(): String {
        if (local.getLogin()) {
            return local.getEmail()
        } else {
            return ""
        }
    }

    suspend fun iniciarSesion(email: String, passwd: String): Boolean {

        // TODO Llamada a la API

        if (true) { // TODO Cambiar: if no error en llamada a la API
            local.setLogin(true)
            local.setEmail(email)

            return true
        } else {
            return false
        }
    }

    suspend fun cerrarSesion() {
        local.clearEmail()
    }

    suspend fun registrarse(email: String, passwd: String): Boolean {

        // TODO Llamada a la API

        if (true) { // TODO Cambiar: if no error en llamada a la API
            local.setLogin(true)
            local.setEmail(email)

            return true
        } else {
            return false
        }
    }
}