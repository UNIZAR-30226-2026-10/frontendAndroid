package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.PerfilRepository
import kotlinx.coroutines.flow.StateFlow

class ActualizarIconoCase(
    private val email: StateFlow<String>,
    private val repo: PerfilRepository
) {
    suspend operator fun invoke(iconId: String): Result<Unit> =
        repo.actualizarCosmetico(email.value, CategoriaCosmetico.ICONO, iconId)
}