package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.PartidaRepository

class CleanPartidaCase(
    private val repository: PartidaRepository
) {
    suspend operator fun invoke() = repository.cleanPartidaState()
}