package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.JugarCrearRepository
import kotlinx.coroutines.flow.StateFlow

class AnadirBotCase(private val repository: JugarCrearRepository, private val username: StateFlow<String>) {
    suspend operator fun invoke() = repository.anadirBot(username.value)
}