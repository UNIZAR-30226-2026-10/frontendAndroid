package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.PerfilRepository
import kotlinx.coroutines.flow.StateFlow

class ObtenerCosmeticosCase(
    private val email: StateFlow<String>,
    private val repo: PerfilRepository
) {
    /**
     * Obtiene el mapa completo de cosméticos disponibles para el usuario actual.
     * Esta es la función recomendada para usar en el ViewModel para evitar múltiples llamadas.
     */
    suspend fun obtenerTodosLosCosmeticos(): Map<CategoriaCosmetico, List<String>> {
        val emailActual = email.value
        return if (emailActual.isNotEmpty()) {
            repo.obtenerCosmeticosDisponibles(emailActual)
        } else {
            emptyMap()
        }
    }

    suspend fun obtenerSkinsEscalera(): List<String> =
        obtenerTodosLosCosmeticos()[CategoriaCosmetico.ESCALERA] ?: emptyList()

    suspend fun obtenerSkinsSerpiente(): List<String> =
        obtenerTodosLosCosmeticos()[CategoriaCosmetico.SERPIENTE] ?: emptyList()

    suspend fun obtenerSkinsFicha(): List<String> =
        obtenerTodosLosCosmeticos()[CategoriaCosmetico.FICHA] ?: emptyList()

    suspend fun obtenerIconos(): List<String> =
        obtenerTodosLosCosmeticos()[CategoriaCosmetico.ICONO] ?: emptyList()
}