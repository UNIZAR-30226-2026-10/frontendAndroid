package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface LoginRegisterRepository {
    // Flujos de estado compartidos para toda la app
    val email: StateFlow<String>
    val username: StateFlow<String>

    suspend fun comprobarLogin(): String
    suspend fun iniciarSesion(email: String, passwd: String): Boolean
    suspend fun cerrarSesion()
    suspend fun registrarse(username: String, email: String, passwd: String): Boolean
}