package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.CategoriaCosmetico
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.PerfilUsuario

interface PerfilRepository {

    // Obtener los datos completos del perfil
    suspend fun obtenerPerfil(email: String): PerfilUsuario

    // Actualizar el nombre en el servidor
    suspend fun actualizarNombre(email: String, nuevoNombre: String): Result<Unit>

    // Cambiar cosmético activo — un método por categoría, reflejando los endpoints reales
    suspend fun actualizarCosmetico(email: String, categoria: CategoriaCosmetico, skinId: String): Result<Unit>

    // Obtener la lista de cosméticos que el usuario posee, por categoría
    suspend fun obtenerCosmeticosDisponibles(email: String): Map<CategoriaCosmetico, List<String>>
}