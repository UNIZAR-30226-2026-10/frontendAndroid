package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.repository

import android.util.Log
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.local.LocalStorage
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.ApiClient
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.ApiService
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.LoginRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.RegisterRequest
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

        return try {
            val response = api.cookieLogin()

            if (response.isSuccessful && response.body() != null) {
                val user = response.body()!!

                // Actualizar estados globales
                _email.value = user.email
                _username.value = user.username

                // Local como logueado
                local.setLogin(true)
                local.setEmail(user.email)

                user.email
            } else {
                // Si la cookie expiró o no existe (401), limpiamos
                cerrarSesion()
                ""
            }
        } catch (e: Exception) {
            ""
        }
    }

    override suspend fun iniciarSesion(email: String, passwd: String): Boolean {
        return try {
            val response = api.login(LoginRequest(email, passwd))

            if (response.isSuccessful && response.body() != null) {
                val user = response.body()!!

                // Local como logueado
                local.setLogin(true)
                local.setEmail(user.email)

                // Actualizar estados globales
                _email.value = user.email
                _username.value = user.username
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun cerrarSesion() {
        local.clearLogin()
        ApiClient.clearCookies()
        _email.value = ""
        _username.value = ""
    }

    override suspend fun registrarse(username: String, email: String, passwd: String): Boolean {

        return try {
            val response = api.register(RegisterRequest(email, username, passwd))

            if (response.isSuccessful && response.body() != null) {
                local.setLogin(true)
                local.setEmail(email)
                _email.value = email
                _username.value = username

                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.e("API_ERROR", "Error: ${e.message}")
            false
        }
    }
}