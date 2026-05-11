package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.PerfilRepository
import kotlinx.coroutines.flow.StateFlow

class ActualizarSkinCase(
    private val email: StateFlow<String>,
    private val repo: PerfilRepository
) {
    suspend operator fun invoke(categoria: CategoriaCosmetico, skinId: String) =
        repo.actualizarCosmetico(email.value, categoria, skinId)
}