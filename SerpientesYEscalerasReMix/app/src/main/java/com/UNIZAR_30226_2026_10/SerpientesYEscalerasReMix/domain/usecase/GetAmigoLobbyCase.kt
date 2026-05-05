package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.AmigosRepository

class GetAmigoLobbyCase(private val repository: AmigosRepository) {
    suspend operator fun invoke(nombre: String) = repository.getAmigoLobby(nombre)
}
