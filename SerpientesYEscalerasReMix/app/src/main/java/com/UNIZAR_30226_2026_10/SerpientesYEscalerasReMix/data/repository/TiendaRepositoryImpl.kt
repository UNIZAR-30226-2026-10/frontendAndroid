package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.repository

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.TiendaAPIService
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Producto
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.toDomain
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.TiendaRepository
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.local.LocalStorage
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.ComprarProductoRequest

class TiendaRepositoryImpl(
    private val apiService: TiendaAPIService,
    private val local: LocalStorage
) : TiendaRepository {

    override suspend fun getProductos(): List<Producto> {
        // Llamada a la API para obtener los productos
        val response = apiService.getProductos(local.getEmail())
        // Convertir los DTOs a modelos de dominio y devolver la lista de productos
        return response.map { it.toDomain() }
    }

    // Necesita como param un JSON con el atributo cosmetic_name
    override suspend fun comprarProducto(producto: Producto): Boolean {
        // Llamada a la API para comprar el producto
        val response = apiService.comprarProducto(local.getEmail(), ComprarProductoRequest(producto.nombre))
        // Devolver true si la compra fue exitosa, false en caso contrario
        if (response.isSuccessful) {
            // Actualizar el saldo del usuario después de una compra exitosa
            val nuevoSaldo = local.getSaldo() - producto.precio
            local.setSaldo(nuevoSaldo)
        }
        return response.isSuccessful
    }

    override suspend fun getSaldo(): Int {
        return local.getSaldo()
    }
}