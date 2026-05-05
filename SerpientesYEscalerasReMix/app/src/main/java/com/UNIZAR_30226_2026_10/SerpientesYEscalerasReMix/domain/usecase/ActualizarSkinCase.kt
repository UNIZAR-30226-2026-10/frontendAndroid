package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import kotlinx.coroutines.flow.StateFlow

class ActualizarSkinCase(
    private val email: StateFlow<String>
) {
    suspend operator fun invoke(categoria: CategoriaCosmetico, skinId: String) {
        when (categoria) {
            CategoriaCosmetico.ESCALERA  -> { /* TODO: PUT /api/users/${email.value}/stair */ }
            CategoriaCosmetico.SERPIENTE -> { /* TODO: Pendiente de API */ }
            CategoriaCosmetico.FICHA     -> { /* TODO: PUT /api/users/${email.value}/pawn */ }
            CategoriaCosmetico.ICONO     -> { /* TODO: PUT /api/users/${email.value}/icon */ }
        }
    }
}