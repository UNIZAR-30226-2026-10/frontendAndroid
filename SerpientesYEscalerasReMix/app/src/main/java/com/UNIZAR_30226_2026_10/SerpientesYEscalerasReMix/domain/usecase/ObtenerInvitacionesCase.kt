package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.AmigosRepository
import kotlinx.coroutines.flow.StateFlow

class ObtenerInvitacionesCase(private val repository: AmigosRepository, private val username: StateFlow<String>) {
    suspend operator fun invoke() = repository.obtenerInvitaciones(username.value)
}
