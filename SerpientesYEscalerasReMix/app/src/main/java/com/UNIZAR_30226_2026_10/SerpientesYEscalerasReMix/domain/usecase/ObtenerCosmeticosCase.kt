package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.PerfilRepository
import kotlinx.coroutines.flow.StateFlow

class ObtenerCosmeticosCase(
    private val email: StateFlow<String>,
    private val repo: PerfilRepository
) {
    suspend fun obtenerSkinsEscalera(): List<String> =
        repo.obtenerCosmeticosDisponibles(email.value)[CategoriaCosmetico.ESCALERA] ?: emptyList()

    suspend fun obtenerSkinsSerpiente(): List<String> =
        repo.obtenerCosmeticosDisponibles(email.value)[CategoriaCosmetico.SERPIENTE] ?: emptyList()

    suspend fun obtenerSkinsFicha(): List<String> =
        repo.obtenerCosmeticosDisponibles(email.value)[CategoriaCosmetico.FICHA] ?: emptyList()

    suspend fun obtenerIconos(): List<String> =
        repo.obtenerCosmeticosDisponibles(email.value)[CategoriaCosmetico.ICONO] ?: emptyList()
}