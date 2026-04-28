package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.ConexionRepository

class PruebaConexionCase(private val repository: ConexionRepository) {
    suspend operator fun invoke(): Boolean = repository.pruebaConexionAPI()
}