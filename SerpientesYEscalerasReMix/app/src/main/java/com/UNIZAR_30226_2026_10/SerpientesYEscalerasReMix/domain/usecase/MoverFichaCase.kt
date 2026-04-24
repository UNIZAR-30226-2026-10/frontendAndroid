package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Movimiento
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.PartidaRepository
import kotlinx.coroutines.flow.StateFlow

class MoverFichaCase(
    private val repository: PartidaRepository,
    private val email: StateFlow<String>,
    private val matchId: StateFlow<String>
) {

    suspend operator fun invoke(): Pair<Int, List<Movimiento>> {
        // Realizar tirada
        return repository.lanzarDado(matchId.value, email.value)
    }

    fun filtrarMovimientosPorFicha(movimientos: List<Movimiento>, fichaId: Int): List<Movimiento> {
        // Filtrado de casillas por ficha
        return movimientos.filter { it.fichaId == fichaId }
    }

    fun bifurcacion
}
