package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.repository

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.local.LocalStorage
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.MazosRepository
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Mazo
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.MazoDto
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.toDomain
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Carta
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.CartaDto
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.ApiService
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Calidad
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Tipo_Carta
import kotlin.String

class MazosRepositoryImpl(
    private val apiService: ApiService
) : MazosRepository {

    override suspend fun getMazos(email: String): List<Mazo> {
        // Llamada a la API para obtener los mazos del usuario
        val response = apiService.getMazos(email)
        if (!response.isSuccessful) {
            throw IllegalStateException("Error getMazos: ${response.code()}")
        }
        val body = response.body() ?: emptyList()
        // Convertir la respuesta a la lista de mazos del dominio
        return body.map { it.toDomain() }
    }

    override suspend fun getCartasMazo(email: String, id: String): List<Carta> {
        // Llamada a la API para obtener las cartas del mazo con el id dado
        val response = apiService.getCartasMazo(email, id)
        if (!response.isSuccessful) {
            throw IllegalStateException("Error getCartasMazo: ${response.code()}")
        }
        val body = response.body() ?: emptyList()
        // Convertir la respuesta a la lista de cartas del dominio
        return body.map { it.toDomain() }
    }

    override suspend fun crearMazo(email: String, nuevoMazo: Mazo): Boolean{
        // Llamada a la API para crear un nuevo mazo con el nombre dado y una lista de cartas vacia
        val mazoDto = MazoDto(
            nombre = nuevoMazo.nombre,
            cartas = nuevoMazo.cartas.map { carta ->
                CartaDto(
                    nombre = carta.nombre,
                    calidad = carta.calidad.toString(),
                    tipo = carta.tipo.toString(),
                    descripcion = carta.descripcion
                )
            }
        )
        val response = apiService.crearMazo(email, mazoDto)
        if (!response.isSuccessful) {
            throw IllegalStateException("Error crearMazo: ${response.code()}")
        }
        // Devolver true si la respuesta es exitosa, false en caso contrario
        return response.isSuccessful
    }

    override suspend fun eliminarMazo(email: String, id: String): Boolean {
        // Llamada a la API para eliminar el mazo con el id dado
        val response = apiService.eliminarMazo(email, id)
        if (!response.isSuccessful) {
            throw IllegalStateException("Error eliminarMazo: ${response.code()}")
        }
        // Devolver true si la respuesta es exitosa, false en caso contrario
        return response.isSuccessful
    }

    override suspend fun editarMazo(
        email: String,
        id: String,
        nuevoNombre: String?,
        nuevasCartas: List<Carta>?,
        eliminarCartas: List<Carta>?
    ): Boolean {
        // Llamada a la API para editar el mazo con el id dado, cambiando su nombre y/o su lista de cartas
        val nuevasCartasDto = nuevasCartas?.map { carta ->
            CartaDto(
                nombre = carta.nombre,
                calidad = carta.calidad.toString(),
                tipo = carta.tipo.toString(),
                descripcion = carta.descripcion
            )
        }
        val eliminarCartasDto = eliminarCartas?.map { carta ->
            CartaDto(
                nombre = carta.nombre,
                calidad = carta.calidad.toString(),
                tipo = carta.tipo.toString(),
                descripcion = carta.descripcion
            )
        }
        val response = apiService.editarMazo(email, id, nuevoNombre, nuevasCartasDto, eliminarCartasDto)
        if (!response.isSuccessful) {
            throw IllegalStateException("Error editarMazo: ${response.code()}")
        }
        // Devolver true si la respuesta es exitosa, false en caso contrario
        return response.isSuccessful
    }

    override suspend fun getCartasDisponibles(email: String): List<Carta> {
        // Llamada a la API para obtener las cartas disponibles para añadir a los mazos
        val response = apiService.getCartasDisponibles(email)
        if (!response.isSuccessful) {
            throw IllegalStateException("Error getCartasDisponibles: ${response.code()}")
        }
        val body = response.body() ?: emptyList()
        // Convertir la respuesta a la lista de cartas del dominio
        return body.map { it.toDomain() }
    }

}