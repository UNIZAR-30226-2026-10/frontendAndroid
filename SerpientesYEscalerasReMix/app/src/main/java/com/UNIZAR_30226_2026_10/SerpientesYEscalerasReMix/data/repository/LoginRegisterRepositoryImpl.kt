package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.repository

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.local.LocalStorage
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.ApiService
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.LoginRegisterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginRegisterRepositoryImpl(
    private val api: ApiService,
    private val local: LocalStorage
) : LoginRegisterRepository {

    private val _email = MutableStateFlow("")
    override val email: StateFlow<String> = _email.asStateFlow()

    private val _username = MutableStateFlow("")
    override val username: StateFlow<String> = _username.asStateFlow()

    override suspend fun comprobarLogin(): String {
        if (local.getLogin()) {
            val email = local.getEmail()
            val passwd = local.getPasswd()
            
            // Iniciamos sesión automáticamente
            iniciarSesion(email, passwd)
            
            return email
        } else {
            return ""
        }
    }

    override suspend fun iniciarSesion(email: String, passwd: String): Boolean {
        // TODO: Implementar llamada real usando api.apiService
        val loginExitoso = true

        if (loginExitoso) {
            local.setLogin(true)
            local.setEmail(email)
            _email.value = email
            _username.value = "Usuario" // TODO: Obtener de la respuesta de la API
            return true
        }
        return false
    }

    override suspend fun cerrarSesion() {
        local.clearLogin()
        _email.value = ""
        _username.value = ""
    }

    override suspend fun registrarse(username: String, email: String, passwd: String): Boolean {
        // TODO: Implementar llamada real usando api.apiService
        val registroExitoso = true

        if (registroExitoso) {
            local.setLogin(true)
            local.setEmail(email)
            local.setPasswd(passwd)
            _email.value = email
            _username.value = username
            return true
        }
        return false
    }
}