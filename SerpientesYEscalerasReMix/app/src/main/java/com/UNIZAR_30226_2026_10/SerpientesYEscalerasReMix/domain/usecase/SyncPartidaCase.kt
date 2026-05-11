package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.PartidaRepository
import kotlinx.coroutines.flow.StateFlow

class SyncPartidaCase(
    private val repository: PartidaRepository,
    private val username: StateFlow<String>,
    private val matchId: StateFlow<String>
) {

    suspend operator fun invoke() {
        if (matchId.value.isNotEmpty()) {
            repository.fetchEstadoCompleto(matchId.value, username.value)
        }
    }
}