package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import kotlinx.coroutines.flow.StateFlow

class ActualizarIconoCase(
    private val email: StateFlow<String>
) {
    suspend operator fun invoke(iconId: String) {
        // TODO: PUT /api/users/${email.value}/icon
    }
}