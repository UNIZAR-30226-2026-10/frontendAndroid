package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.repository

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.ApiClient
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.PerfilRepository
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CategoriaCosmetico
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.PerfilUsuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PerfilRepositoryImpl : PerfilRepository { // <--- Importante heredar de la interfaz
    private val api = ApiClient.apiService

    override suspend fun obtenerPerfil(email: String): PerfilUsuario = withContext(Dispatchers.IO) {
        val response = api.getUserProfile(email)
        if (response.isSuccessful && response.body() != null) {
            val r = response.body()!!
            PerfilUsuario(r.nombre, r.victorias, r.derrotas, 0, r.iconoActual, r.skinEscaleraActual, r.skinSerpienteActual, r.skinFichaActual)
        } else throw Exception("Error al cargar perfil")
    }

    override suspend fun actualizarNombre(email: String, nuevoNombre: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val resp = api.updateUsername(email, mapOf("username" to nuevoNombre))
            if (resp.isSuccessful) Result.success(Unit) else Result.failure(Exception("Error API"))
        } catch (e: Exception) { Result.failure(e) }
    }

    override suspend fun actualizarCosmetico(email: String, categoria: CategoriaCosmetico, skinId: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            // Aquí 'categoria.name' funcionará porque CategoriaCosmetico es un enum
            val resp = api.updateCosmetic(email, mapOf("type" to categoria.name, "id" to skinId))
            if (resp.isSuccessful) Result.success(Unit) else Result.failure(Exception("Error API"))
        } catch (e: Exception) { Result.failure(e) }
    }

    override suspend fun obtenerCosmeticosDisponibles(): Map<CategoriaCosmetico, List<String>> {
        return mapOf(
            CategoriaCosmetico.ESCALERA to listOf("Clásica", "Madera"),
            CategoriaCosmetico.SERPIENTE to listOf("Cobra", "Pitón"),
            CategoriaCosmetico.FICHA to listOf("Roja", "Azul")
        )
    }
}