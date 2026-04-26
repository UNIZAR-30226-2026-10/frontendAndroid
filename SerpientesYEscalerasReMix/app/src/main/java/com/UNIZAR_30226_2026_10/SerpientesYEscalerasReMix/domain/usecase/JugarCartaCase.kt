package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase;

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.PartidaRepository
import kotlinx.coroutines.flow.StateFlow

class JugarCartaCase(
    private val repository: PartidaRepository,
    private val email: StateFlow<String>,
    private val matchId: StateFlow<String>
) {
    suspend operator fun invoke(
            cartaId: String,
            target: String? = null,
            inicio: Int? = null,
            fin: Int? = null
    ) {
        repository.jugarCarta(matchId.value, email.value, cartaId, target, inicio, fin)
    }
}
