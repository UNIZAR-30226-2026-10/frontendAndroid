package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.MazosRepository
import kotlinx.coroutines.flow.StateFlow

class GetMazosCase(
    private val mazosRepository: MazosRepository,
    private val email: StateFlow<String>
) {
    suspend operator fun invoke() {
        mazosRepository.getMazos(email.value)
    }
}