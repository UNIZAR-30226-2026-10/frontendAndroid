package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.JugarCrearRepository

class ObtenerTablerosCase(private val repository: JugarCrearRepository) {
    suspend operator fun invoke(): List<String> = repository.getBoards()
}
