package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.AmigosRepository
import kotlinx.coroutines.flow.StateFlow

class EliminarAmigoCase(private val repository: AmigosRepository, private val email: StateFlow<String>) {
    suspend operator fun invoke(nombre: String) = repository.eliminarAmigo(nombre, email.value)
}
