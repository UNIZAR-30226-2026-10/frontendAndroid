package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.AmigosRepository

class EliminarAmigoCase(private val repository: AmigosRepository) {
    suspend operator fun invoke(nombre: String) = repository.eliminarAmigo(nombre)
}
