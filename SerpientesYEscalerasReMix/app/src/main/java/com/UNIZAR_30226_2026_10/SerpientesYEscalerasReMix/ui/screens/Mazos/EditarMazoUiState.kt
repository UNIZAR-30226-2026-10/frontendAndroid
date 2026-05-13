package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Mazos

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Carta
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Mazo

sealed class EditarMazoUiState {
    // Al abrir la pantalla y carga de la API
    object Loading : EditarMazoUiState()

    // Cuando los datos llegan correctamente
    data class Success(
        val mazoOriginal: Mazo,
        val mazo: Mazo,
        val cartasDisponibles: List<Carta>,
        val esNuevoMazo: Boolean = false,
        val hasChanges: Boolean = false,
        val saveSuccess: Boolean = false,
        val saving: Boolean = false
    ) : EditarMazoUiState()

    // Si hay un error al cargar los datos
    data class Error(val message: String) : EditarMazoUiState()
}
