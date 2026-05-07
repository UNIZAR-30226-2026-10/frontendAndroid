package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Mazo
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.MazosRepository

class CrearMazoCase(private val mazosRepository: MazosRepository) {
    suspend operator fun invoke(nuevoMazo: Mazo): Boolean {
        return mazosRepository.crearMazo(nuevoMazo)
    }
}