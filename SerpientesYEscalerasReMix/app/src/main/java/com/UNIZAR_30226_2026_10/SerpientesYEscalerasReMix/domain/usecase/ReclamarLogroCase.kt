package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import kotlinx.coroutines.flow.StateFlow

class ReclamarLogroCase(
    private val email: StateFlow<String>
) {
    suspend operator fun invoke(achievementId: String) {
        // TODO: POST /api/users/${email.value}/achievements
        // Body: { "achievement_id": achievementId }
    }
}