package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Mazos

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Mazo


sealed class MazosUiState {
    // Al abrir la pantalla y carga de la API
    object Loading : MazosUiState()

    // Cuando los datos llegan correctamente
    data class Success(
        val mazos: List<Mazo>
    ) : MazosUiState()

    // Si hay un error al cargar los datos
    data class Error(val message: String) : MazosUiState()
}