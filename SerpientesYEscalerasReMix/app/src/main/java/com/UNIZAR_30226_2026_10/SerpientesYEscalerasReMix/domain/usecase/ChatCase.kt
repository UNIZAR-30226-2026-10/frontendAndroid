package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.MsgChat
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.PartidaRepository
import kotlinx.coroutines.flow.StateFlow

class ChatCase(
    private val repository: PartidaRepository,
    private val matchId: StateFlow<String>
) {
    suspend fun enviar(mensaje: MsgChat) = repository.enviarMensaje(matchId.value, mensaje)
    suspend fun recibir() = repository.recibirChat(matchId.value)
}