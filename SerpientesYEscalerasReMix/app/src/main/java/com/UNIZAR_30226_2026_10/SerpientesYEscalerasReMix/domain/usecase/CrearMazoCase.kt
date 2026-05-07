package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.MazosRepository

class CrearMazoCase(private val mazosRepository: MazosRepository) {
    suspend operator fun invoke(nombre: String) {
        mazosRepository.crearMazo(nombre)
    }
}