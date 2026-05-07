package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.AmigosRepository
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.JugarCrearRepository
import kotlinx.coroutines.flow.StateFlow

class ResponderInvitacionCase(
    private val repository: AmigosRepository,
    private val jugarCrearRepository: JugarCrearRepository,
    private val username: StateFlow<String>
) {
    suspend operator fun invoke(lobbyIdInvitacion: String, inviteFrom: String, aceptar: Boolean) {
        val exito =  repository.responderInvitacion(lobbyIdInvitacion, inviteFrom, aceptar, username.value)
        if (aceptar && exito) {
            jugarCrearRepository.setLobbyId(lobbyIdInvitacion)
        }
    }
}
