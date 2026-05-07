package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.MazosRepository

class GetCartasDisponiblesCase(private val mazosRepository: MazosRepository) {
    suspend operator fun invoke() {
        mazosRepository.getCartasDisponibles()
    }
}