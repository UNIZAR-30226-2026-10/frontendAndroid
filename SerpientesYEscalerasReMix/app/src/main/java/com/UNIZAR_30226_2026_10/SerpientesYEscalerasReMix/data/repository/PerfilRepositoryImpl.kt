package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.repository

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.ApiClient
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.ActualizarEscaleraRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.ActualizarFichaRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.ActualizarIconoRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.ActualizarSerpienteRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.PerfilRepository
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CategoriaCosmetico
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.PerfilUsuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class PerfilRepositoryImpl : PerfilRepository {
    private val api = ApiClient.apiService

    override suspend fun obtenerPerfil(email: String): PerfilUsuario = withContext(Dispatchers.IO) {
        val response = api.getUserProfile(email)
        if (response.isSuccessful && response.body() != null) {
            val r = response.body()!!
            PerfilUsuario(
                nombre              = r.nombre,
                victorias           = r.victorias,
                derrotas            = r.derrotas,
                monedas             = r.monedas,
                iconoActual         = r.iconoActual,
                skinEscaleraActual  = r.skinEscaleraActual,
                skinSerpienteActual = r.skinSerpienteActual,
                skinFichaActual     = r.skinFichaActual
            )
        } else {
            throw Exception("Error al cargar perfil: ${response.code()} ${response.message()}")
        }
    }

    override suspend fun actualizarNombre(email: String, nuevoNombre: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val resp = api.updateUsername(email, mapOf("username" to nuevoNombre))
                if (resp.isSuccessful) Result.success(Unit)
                else Result.failure(Exception("Error ${resp.code()}: ${resp.message()}"))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    // Cada categoría usa su propio endpoint PUT según la API
    override suspend fun actualizarCosmetico(
        email: String,
        categoria: CategoriaCosmetico,
        skinId: String
    ): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val resp = when (categoria) {
                CategoriaCosmetico.ICONO     -> api.updateIcon(email,  ActualizarIconoRequest(skinId))
                CategoriaCosmetico.FICHA     -> api.updatePawn(email,  ActualizarFichaRequest(skinId))
                CategoriaCosmetico.SERPIENTE -> api.updateSnake(email, ActualizarSerpienteRequest(skinId))
                CategoriaCosmetico.ESCALERA  -> api.updateStair(email, ActualizarEscaleraRequest(skinId))
            }
            if (resp.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("Error ${resp.code()}: ${resp.message()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Las 4 llamadas se lanzan en paralelo para reducir la latencia total
    override suspend fun obtenerCosmeticosDisponibles(
        email: String
    ): Map<CategoriaCosmetico, List<String>> = withContext(Dispatchers.IO) {
        val iconosDeferred     = async { api.getUserIcons(email) }
        val fichasDeferred     = async { api.getUserPawns(email) }
        val serpientesDeferred = async { api.getUserSnakes(email) }
        val escalerasDeferred  = async { api.getUserStairs(email) }

        val iconosResp     = iconosDeferred.await()
        val fichasResp     = fichasDeferred.await()
        val serpientesResp = serpientesDeferred.await()
        val escalerasResp  = escalerasDeferred.await()

        mapOf(
            CategoriaCosmetico.ICONO     to (iconosResp.body()?.iconos         ?: emptyList()),
            CategoriaCosmetico.FICHA     to (fichasResp.body()?.fichas         ?: emptyList()),
            CategoriaCosmetico.SERPIENTE to (serpientesResp.body()?.serpientes ?: emptyList()),
            CategoriaCosmetico.ESCALERA  to (escalerasResp.body()?.escaleras   ?: emptyList())
        )
    }
}