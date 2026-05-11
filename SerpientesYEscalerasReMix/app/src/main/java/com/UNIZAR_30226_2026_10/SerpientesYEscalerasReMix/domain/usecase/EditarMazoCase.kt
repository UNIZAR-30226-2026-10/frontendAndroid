package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Carta
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.MazosRepository
import kotlinx.coroutines.flow.StateFlow

class EditarMazoCase(
    private val mazosRepository: MazosRepository,
    private val email: StateFlow<String>
) {
    suspend operator fun invoke(id: String, nuevoNombre: String?, nuevasCartas: List<Carta>?, eliminarCartas: List<Carta>?): Boolean {
        return mazosRepository.editarMazo(email.value, id, nuevoNombre, nuevasCartas, eliminarCartas)
    }
}