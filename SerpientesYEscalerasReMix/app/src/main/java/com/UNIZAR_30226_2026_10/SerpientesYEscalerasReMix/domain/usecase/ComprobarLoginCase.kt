package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.LoginRegisterRepository

class ComprobarLoginCase(private val repository: LoginRegisterRepository) {
    suspend operator fun invoke(): String = repository.comprobarLogin()
}