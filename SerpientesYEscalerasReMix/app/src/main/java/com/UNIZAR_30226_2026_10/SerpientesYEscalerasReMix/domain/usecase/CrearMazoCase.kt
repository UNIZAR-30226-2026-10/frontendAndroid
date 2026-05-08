package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Mazo
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.MazosRepository
import kotlinx.coroutines.flow.StateFlow

class CrearMazoCase(
    private val mazosRepository: MazosRepository,
    private val email: StateFlow<String>
) {
    suspend operator fun invoke(nuevoMazo: Mazo): Boolean {
        return mazosRepository.crearMazo(email.value, nuevoMazo)
    }
}