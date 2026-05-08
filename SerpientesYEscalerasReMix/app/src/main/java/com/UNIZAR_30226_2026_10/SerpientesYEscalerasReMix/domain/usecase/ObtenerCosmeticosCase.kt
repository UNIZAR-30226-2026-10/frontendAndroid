package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.PerfilRepository

class ObtenerCosmeticosCase(private val repo: PerfilRepository) {

    suspend fun obtenerSkinsEscalera(): List<String> {
        // Obtenemos el mapa completo del repositorio y filtramos por la categoría
        return repo.obtenerCosmeticosDisponibles()[CategoriaCosmetico.ESCALERA] ?: emptyList()
    }

    suspend fun obtenerSkinsSerpiente(): List<String> {
        return repo.obtenerCosmeticosDisponibles()[CategoriaCosmetico.SERPIENTE] ?: emptyList()
    }

    suspend fun obtenerSkinsFicha(): List<String> {
        return repo.obtenerCosmeticosDisponibles()[CategoriaCosmetico.FICHA] ?: emptyList()
    }

    suspend fun obtenerIconos(): List<String> {
        return repo.obtenerCosmeticosDisponibles()[CategoriaCosmetico.ICONO] ?: emptyList()
    }
}