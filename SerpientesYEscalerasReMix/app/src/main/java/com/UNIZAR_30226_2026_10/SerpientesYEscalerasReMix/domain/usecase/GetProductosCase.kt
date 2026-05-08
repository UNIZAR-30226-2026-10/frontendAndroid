package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Producto
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.TiendaRepository
import kotlinx.coroutines.flow.StateFlow

class GetProductosCase(
    private val tiendaRepository: TiendaRepository,
    private val email: StateFlow<String>
) {
    suspend operator fun invoke(): List<Producto> {
        val productos = tiendaRepository.getProductos(email.value)

        //TODO añadir mas reglas antes de devolver los proucots

        return productos
    }
}
