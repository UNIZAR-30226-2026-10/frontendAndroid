package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Producto


interface TiendaRepository {
    suspend fun comprarProducto(email: String, producto: Producto): Boolean
    suspend fun getProductos(email: String): List<Producto>
    suspend fun getSaldo(email: String): Int
    //suspend fun getHistorialCompras(): List<Compra>

}