package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.LogrosRepository
import kotlinx.coroutines.flow.StateFlow

class ReclamarLogroCase(
    private val email: StateFlow<String>,
    private val repository: LogrosRepository
) {
    // FIX: eliminado el try/catch que capturaba y relanzaba sin hacer nada útil.
    // Las excepciones se propagan directamente al ViewModel.
    suspend operator fun invoke(achievementId: String) {
        repository.postClaimAchievement(
            email         = email.value,
            achievementId = achievementId
        )
    }
}