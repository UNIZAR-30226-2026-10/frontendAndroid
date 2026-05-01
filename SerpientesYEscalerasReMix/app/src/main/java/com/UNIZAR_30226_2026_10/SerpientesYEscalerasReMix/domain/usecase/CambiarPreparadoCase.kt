package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.JugarCrearRepository
import kotlinx.coroutines.flow.StateFlow

class CambiarPreparadoCase(
    private val repository: JugarCrearRepository,
    private val email: StateFlow<String>
) {
    suspend operator fun invoke(listo: Boolean) = repository.cambiarPreparado(email.value, listo)
}