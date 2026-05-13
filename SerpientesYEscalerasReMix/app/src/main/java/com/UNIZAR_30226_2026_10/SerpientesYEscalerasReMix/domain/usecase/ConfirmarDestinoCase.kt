package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Movimiento
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.PartidaRepository
import kotlinx.coroutines.flow.StateFlow

class ConfirmarDestinoCase(
    private val repository: PartidaRepository,
    private val username: StateFlow<String>,
    private val matchId: StateFlow<String>
) {

    suspend operator fun invoke(movimiento: Movimiento) {
        // Confirmar movimiento
        repository.confirmarMovimiento(
            matchId = matchId.value,
            username = username.value,
            fichaId = movimiento.fichaId,
            destinoId = movimiento.casillaId,
            esBifurcacion = false,
            pasosRestantes = if (movimiento.pasosRestantes == 0) null
                             else movimiento.pasosRestantes
        )
    }

    suspend operator fun invoke(movimiento: Movimiento, casillaDir: Int, onEscalera: () -> Unit) {
        // Confirmar movimiento
        repository.confirmarMovimiento(
            matchId = matchId.value,
            username = username.value,
            fichaId = movimiento.fichaId,
            destinoId = movimiento.casillaId,
            esBifurcacion = false,
            pasosRestantes = if (movimiento.pasosRestantes == 0) null
                             else movimiento.pasosRestantes
        )

        val hayEscalera = repository.confirmarMovimiento(
            matchId = matchId.value,
            username = username.value,
            fichaId = movimiento.fichaId,
            destinoId = casillaDir,
            esBifurcacion = true,
            pasosRestantes = if (movimiento.pasosRestantes == 0) null
            else movimiento.pasosRestantes
        )

        if (hayEscalera) {
            onEscalera()
        }
    }
}