package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.TiendaRepository
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Producto

class ComprarProductoCase(private val tiendaRepository: TiendaRepository) {
    suspend operator fun invoke(producto: Producto): Boolean {
        //TODO añadir reglas de negocio antes de comprar el producto
        return tiendaRepository.comprarProducto(producto)
    }
}