package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Producto
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.TiendaRepository

class GetProductosCase(private  val tiendaRepository: TiendaRepository) {
    suspend operator fun invoke(): List<Producto> {
        val productos = return tiendaRepository.getProductos()

        //TODO añadir mas reglas antes de devolver los proucots

        return productos
    }
}