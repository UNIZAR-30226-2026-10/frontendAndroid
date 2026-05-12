package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.TiendaRepository
import kotlinx.coroutines.flow.StateFlow

class GetSaldoCase(
    private val tiendaRepository: TiendaRepository,
    private val email: StateFlow<String>
) {
    suspend operator fun invoke(): Int {
        return tiendaRepository.getSaldo(email.value)
    }
}