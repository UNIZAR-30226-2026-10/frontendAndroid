package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.repository

import android.util.Log
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.ApiService
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

class PerfilRepositoryImpl(
    private val apiService: ApiService
) : PerfilRepository {

    override suspend fun obtenerPerfil(email: String): PerfilUsuario = withContext(Dispatchers.IO) {
        val response = apiService.getUserProfile(email)

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
            // ERROR: En Kotlin con Retrofit, code() es una función o propiedad.
            // Si te da error "cannot be invoked", usa .code sin paréntesis.
            throw Exception("Error al cargar perfil: ${response.code()} ${response.message()}")
        }
    }

    override suspend fun actualizarNombre(email: String, nuevoNombre: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val resp = apiService.updateUsername(email, mapOf("username" to nuevoNombre))
                if (resp.isSuccessful) Result.success(Unit)
                else Result.failure(Exception("Error ${resp.code()}: ${resp.message()}"))
            } catch (e: Exception) {
                // ERROR: "Cannot infer type". Hay que especificar el tipo de Result
                Result.failure<Unit>(e)
            }
        }

    override suspend fun actualizarCosmetico(
        email: String,
        categoria: CategoriaCosmetico,
        skinId: String
    ): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val resp = when (categoria) {
                CategoriaCosmetico.ICONO     -> apiService.updateIcon(email,  ActualizarIconoRequest(skinId))
                CategoriaCosmetico.FICHA     -> apiService.updatePawn(email,  ActualizarFichaRequest(skinId))
                CategoriaCosmetico.SERPIENTE -> apiService.updateSnake(email, ActualizarSerpienteRequest(skinId))
                CategoriaCosmetico.ESCALERA  -> apiService.updateStair(email, ActualizarEscaleraRequest(skinId))
            }
            if (resp.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("Error ${resp.code()}: ${resp.message()}"))
        } catch (e: Exception) {
            Result.failure<Unit>(e)
        }
    }

    // REEMPLAZA TU FUNCIÓN POR ESTA (He quitado el .string() del log)
    override suspend fun obtenerCosmeticosDisponibles(
        email: String
    ): Map<CategoriaCosmetico, List<String>> = withContext(Dispatchers.IO) {
        val iconosDeferred     = async { apiService.getUserIcons(email) }
        val fichasDeferred     = async { apiService.getUserPawns(email) }
        val serpientesDeferred = async { apiService.getUserSnakes(email) }
        val escalerasDeferred  = async { apiService.getUserStairs(email) }

        val iconosResp     = iconosDeferred.await()
        val fichasResp     = fichasDeferred.await()
        val serpientesResp = serpientesDeferred.await()
        val escalerasResp  = escalerasDeferred.await()

        // LOGS SEGUROS (Sin consumir el stream)
        Log.d("PERFIL_DEBUG", "Iconos OK: ${iconosResp.isSuccessful} Código: ${iconosResp.code()}")

        mapOf(
            CategoriaCosmetico.ICONO     to (iconosResp.body()?.iconos         ?: emptyList()),
            CategoriaCosmetico.FICHA     to (fichasResp.body()?.fichas         ?: emptyList()),
            CategoriaCosmetico.SERPIENTE to (serpientesResp.body()?.serpientes ?: emptyList()),
            CategoriaCosmetico.ESCALERA  to (escalerasResp.body()?.escaleras   ?: emptyList())
        )
    }
}