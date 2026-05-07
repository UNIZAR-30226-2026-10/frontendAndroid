package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.repository

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.local.LocalStorage
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.MazosRepository
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Mazo
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.MazoDto
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.toDomain
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Carta
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.CartaDto
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.MazosAPIService

class MazosRepositoryImpl(
    private val apiService: MazosAPIService,
    private val local: LocalStorage
) : MazosRepository {

    override suspend fun getMazos(): List<Mazo> {
        // Llamada a la API para obtener los mazos del usuario
        val response = apiService.getMazos((local.getEmail()))
        // Convertir la respuesta a la lista de mazos del dominio
        return response.map { it.toDomain() }
    }

    override suspend fun getCartasMazo(id: String): List<Carta> {
        // Llamada a la API para obtener las cartas del mazo con el id dado
        val response = apiService.getCartasMazo((local.getEmail()), id)
        // Convertir la respuesta a la lista de cartas del dominio
        return response.map { it.toDomain() }
    }

    override suspend fun crearMazo(nuevoMazo: Mazo): Boolean{
        // Llamada a la API para crear un nuevo mazo con el nombre dado y una lista de cartas vacia
        val mazoDto = MazoDto(
            nombre = nuevoMazo.nombre,
            cartas = nuevoMazo.cartas.map { carta ->
                CartaDto(
                    nombre = carta.nombre,
                    tipo = carta.tipo,
                    calidad = carta.calidad,
                    descripcion = carta.descripcion
                )
            }
        )
        val response = apiService.crearMazo((local.getEmail()), mazoDto)
        // Devolver true si la respuesta es exitosa, false en caso contrario
        return response.isSuccessful
    }

    override suspend fun eliminarMazo(id: String): Boolean {
        // Llamada a la API para eliminar el mazo con el id dado
        val response = apiService.eliminarMazo((local.getEmail()), id)
        // Devolver true si la respuesta es exitosa, false en caso contrario
        return response.isSuccessful
    }

    override suspend fun editarMazo(
        id: String,
        nuevoNombre: String?,
        nuevasCartas: List<Carta>?,
        eliminarCartas: List<Carta>?
    ): Boolean {
        // Llamada a la API para editar el mazo con el id dado, cambiando su nombre y/o su lista de cartas
        val nuevasCartasDto = nuevasCartas?.map { carta ->
            CartaDto(
                nombre = carta.nombre,
                tipo = carta.tipo,
                calidad = carta.calidad,
                descripcion = carta.descripcion
            )
        }
        val eliminarCartasDto = eliminarCartas?.map { carta ->
            CartaDto(
                nombre = carta.nombre,
                tipo = carta.tipo,
                calidad = carta.calidad,
                descripcion = carta.descripcion
            )
        }
        val response = apiService.editarMazo((local.getEmail()), id, nuevoNombre, nuevasCartasDto, eliminarCartasDto)
        // Devolver true si la respuesta es exitosa, false en caso contrario
        return response.isSuccessful
    }

    override suspend fun getCartasDisponibles(): List<Carta> {
        // Llamada a la API para obtener las cartas disponibles para añadir a los mazos
        val response = apiService.getCartasDisponibles((local.getEmail()))
        // Convertir la respuesta a la lista de cartas del dominio
        return response.map { it.toDomain() }
    }

}