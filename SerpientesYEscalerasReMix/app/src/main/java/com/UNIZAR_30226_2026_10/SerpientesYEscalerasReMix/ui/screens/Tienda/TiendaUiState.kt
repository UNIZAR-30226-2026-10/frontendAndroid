package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Tienda

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Producto

sealed class TiendaUiState{
    // Al abrir la pantall y carga de la API
    object Loading: TiendaUiState()

    // Cuando los datos llegan correctamente
    data class Success(
        val productos: List<Producto>,
        val saldo: Int
    ) : TiendaUiState()

    // Si hay un error al cargar los datos
    data class Error(val message: String) : TiendaUiState()
}