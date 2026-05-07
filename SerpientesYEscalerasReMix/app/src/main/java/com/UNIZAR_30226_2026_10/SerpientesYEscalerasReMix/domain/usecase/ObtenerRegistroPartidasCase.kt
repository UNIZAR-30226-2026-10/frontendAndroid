package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.RegistroPartida
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.JugarContinuarRepository
import kotlinx.coroutines.flow.StateFlow

class ObtenerRegistroPartidasCase(
    private val repository: JugarContinuarRepository,
    private val email: StateFlow<String>
) {
    suspend fun obtenerPartidas(): List<RegistroPartida> {
        return repository.obtenerPartidas(email.value)
    }
}
