package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.PartidaRepository
import kotlinx.coroutines.flow.StateFlow

class SyncPartidaCase(
    private val repository: PartidaRepository,
    private val email: StateFlow<String>,
    private val matchId: StateFlow<String>
) {

    private var init: Boolean = false

    suspend operator fun invoke() {

        if (repository.jugadores.value.jugadores.isEmpty()) {
            if (matchId.value.isNotEmpty()) {
                repository.fetchEstadoCompleto(matchId.value, email.value)
                init = true
            }
        } else {

            val turno = repository.jugadores.value.turno
            val jugadorTurno = repository.jugadores.value.jugadores[turno].email
            val esMiTurno = jugadorTurno == email.value

            if (!init || esMiTurno) {
                repository.fetchEstadoCompleto(matchId.value, email.value)
                init = true
            }
        }
    }
}