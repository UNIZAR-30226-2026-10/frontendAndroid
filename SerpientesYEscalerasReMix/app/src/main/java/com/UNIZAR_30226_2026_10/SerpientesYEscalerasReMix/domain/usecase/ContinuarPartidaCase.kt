package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.PartidaRepository

class ContinuarPartidaCase(private val repository: PartidaRepository,) {
    suspend fun invoke(matchId: String){
        repository.setMatchId(matchId)
    }
}