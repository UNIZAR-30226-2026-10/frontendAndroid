package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.repository

import android.util.Log
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.ApiService
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.PartidaRegistroGetReply
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.RegistroPartida
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.JugarContinuarRepository

class JugarContinuarRepositoryImpl(private val api: ApiService) : JugarContinuarRepository {
    override suspend fun obtenerPartidas(email: String): List<RegistroPartida> {
        return try {
            val response = api.getMatches(email)
            if (response.isSuccessful) {
                response.body()?.matches?.map { it.toDomain() } ?: emptyList()
            } else {
                Log.e("API_ERROR", "Error obteniendo partidas: ${response.errorBody()?.string()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("API_ERROR", "Excepción obteniendo partidas", e)
            emptyList()
        }
    }

    private fun PartidaRegistroGetReply.toDomain() = RegistroPartida(
        fecha = fecha,
        jugadores = jugadores.joinToString(", "),
        id = iD,
        mapa = mapa
    )
}
