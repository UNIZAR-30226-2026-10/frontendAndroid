package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Movimiento
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.PartidaRepository
import kotlinx.coroutines.flow.StateFlow

class ConfirmarDestinoCase(
    private val repository: PartidaRepository,
    private val email: StateFlow<String>,
    private val matchId: StateFlow<String>
) {

    suspend operator fun invoke(movimiento: Movimiento): List<Movimiento> {
        // Confirmar movimiento
        return repository.confirmarMovimiento(
            matchId = matchId.value,
            email = email.value,
            fichaId = movimiento.fichaId,
            destinoId = movimiento.casillaId,
            pasosRestantes = if (movimiento.pasosRestantes == 0) null
                             else movimiento.pasosRestantes
        )
    }

    suspend operator fun invoke(movimiento: Movimiento, casillaDir: Int): List<Movimiento> {
        // Confirmar movimiento
        return repository.confirmarMovimiento(
            matchId = matchId.value,
            email = email.value,
            fichaId = movimiento.fichaId,
            destinoId = movimiento.fichaId,
            pasosRestantes = movimiento.pasosRestantes
        ) // TODO añadir decision mediante casillaDir
    }
}