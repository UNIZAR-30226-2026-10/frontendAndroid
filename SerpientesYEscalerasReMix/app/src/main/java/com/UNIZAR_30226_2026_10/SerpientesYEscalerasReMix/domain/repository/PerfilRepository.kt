package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.PerfilUsuario
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CategoriaCosmetico

interface PerfilRepository {
    // Obtener los datos completos del perfil
    suspend fun obtenerPerfil(email: String): PerfilUsuario

    // Actualizar el nombre en el servidor
    suspend fun actualizarNombre(email: String, nuevoNombre: String): Result<Unit>

    // Cambiar la skin/icono actual
    suspend fun actualizarCosmetico(email: String, categoria: CategoriaCosmetico, skinId: String): Result<Unit>

    // Obtener la lista de strings (IDs) de cosméticos que el usuario posee
    suspend fun obtenerCosmeticosDisponibles(): Map<CategoriaCosmetico, List<String>>
}
