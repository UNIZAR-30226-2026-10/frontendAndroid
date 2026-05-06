package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.AmigosRepository
import kotlinx.coroutines.flow.StateFlow

class InvitarAmigoLobbyCase(private val repository: AmigosRepository, private val username: StateFlow<String>, private val lobbyId: StateFlow<String>) {
    suspend operator fun invoke(nombre: String) = repository.invitarAmigoLobby(nombre, username.value, lobbyId.value)
}
