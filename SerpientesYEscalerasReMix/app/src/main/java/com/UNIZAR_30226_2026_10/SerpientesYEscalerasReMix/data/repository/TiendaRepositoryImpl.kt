package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.repository

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.network.TiendaAPIService
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Producto
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.toDomain
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.TiendaRepository
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.UserRepository

class TiendaRepositoryImpl(
    private val apiService: TiendaAPIService,
    private val userRepository: UserRepository
) : TiendaRepository {

    override suspend fun getProductos(): List<Producto> {
        // Llamada a la API para obtener los productos
        val response = apiService.getProductos()
        // Convertir los DTOs a modelos de dominio y devolver la lista de productos
        return response.map { it.toDomain() }
    }

    override suspend fun comprarProducto(producto: Producto): Boolean {
        // Llamada a la API para comprar el producto
        val response = apiService.comprarProducto(producto.nombre)
        // Devolver true si la compra fue exitosa, false en caso contrario
        if (response.isSuccessful) {
            getSaldo()
        }
        return response.isSuccessful
    }

    override suspend fun getSaldo(): Int {
        // Llamada a la API para obtener el saldo del usuario
        val saldo = apiService.getSaldo()
        // Actualizar el saldo en el UserRepository
        userRepository.actualizarSaldo(saldo)
        // Devolver el saldo obtenido
        return saldo
    }
}