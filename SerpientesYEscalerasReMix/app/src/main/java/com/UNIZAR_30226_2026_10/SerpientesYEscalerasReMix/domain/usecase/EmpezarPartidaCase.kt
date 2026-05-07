package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.JugarCrearRepository
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.PartidaRepository

class EmpezarPartidaCase(
    private val JugarCrearRepository: JugarCrearRepository,
    private val partidaRepository: PartidaRepository
) {
    suspend operator fun invoke(id: String, onSucces: () -> Unit) {
        val matchId = JugarCrearRepository.empezarPartida()
        partidaRepository.setMatchId(matchId)
    }
}