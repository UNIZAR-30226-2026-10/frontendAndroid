package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Compra
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Producto


interface TiendaRepository {
    suspend fun comprarProducto(nombreProducto: String): Boolean
    suspend fun getProductos(): List<Producto>
    suspend fun getHistorialCompras(): List<Compra>
}