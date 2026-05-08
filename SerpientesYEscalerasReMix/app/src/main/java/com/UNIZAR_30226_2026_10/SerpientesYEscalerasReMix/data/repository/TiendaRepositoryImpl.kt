package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.repository

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.ApiService
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Producto
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.toDomain
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.TiendaRepository
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.local.LocalStorage
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.ComprarProductoRequest
import kotlinx.coroutines.flow.StateFlow

class TiendaRepositoryImpl(
    private val apiService: ApiService
) : TiendaRepository {

    override suspend fun getProductos(email: String): List<Producto> {
        // Llamada a la API para obtener los productos
        val response = apiService.getProductos(email)
        // Convertir los DTOs a modelos de dominio y devolver la lista de productos
        return response.map { it.toDomain() }
    }

    // Necesita como param un JSON con el atributo cosmetic_name
    override suspend fun comprarProducto(email: String, producto: Producto): Boolean {
        // Llamada a la API para comprar el producto
        val response = apiService.comprarProducto(email, ComprarProductoRequest(producto.nombreId))
        // Devolver true si la compra fue exitosa, false en caso contrario
        if (response.isSuccessful) {
            // TODO MIRAR DE CAMBIAR EN PANTALLA LA CURRENCY
        }
        return response.isSuccessful
    }

    override suspend fun getSaldo(email: String): Int {
        return apiService.getSaldo(email).toDomain()
    }
}