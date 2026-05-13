package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.PerfilUsuario
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.PerfilRepository
import kotlinx.coroutines.flow.StateFlow

class ObtenerPerfilCase(
    private val email: StateFlow<String>,
    private val repo: PerfilRepository
) {
    suspend operator fun invoke(): PerfilUsuario = repo.obtenerPerfil(email.value)
}