package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Tienda

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.GetProductosCase
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.ComprarProductoCase
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Producto
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch



class TiendaViewModel (
    private val getProductosCase: GetProductosCase,
    private val comprarProductoCase: ComprarProductoCase
) : ViewModel() {

    // Estado privado
    private val _uiState = MutableStateFlow<TiendaUiState>(TiendaUiState.Loading)

    // Estado público inmutable
    val uiState: StateFlow<TiendaUiState> = _uiState

    // Para que solo exista un único viewmodel durante toda la ejecución de la app
    companion object {
        fun factory(
            getProductosCase: GetProductosCase,
            comprarProductoCase: ComprarProductoCase
        ) : ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TiendaViewModel(getProductosCase, comprarProductoCase) as T
                }
            }
    }


    fun obtenerProductos(): List<Producto> {
        viewModelScope.launch {
            try {
                val lista = getProductosCase()

            }
        }
    }

    fun comprarProducto(productoId: String) {
        comprarProductoCase(productoId)
    }

}