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
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CaseFacade

class TiendaViewModel (private val cf: CaseFacade) : ViewModel() {

    // Estado privado
    private val _uiState = MutableStateFlow<TiendaUiState>(TiendaUiState.Loading)

    // Estado público inmutable
    val uiState: StateFlow<TiendaUiState> = _uiState

    companion object {
        fun factory(cf: CaseFacade): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TiendaViewModel(cf) as T
            }
        }
    }

    init {
        fetchProductos()

        viewModelScope.launch {
            try {
                val saldo = cf.getSaldoCase()
                actualizarEstadoConSaldo(saldo)
            } catch (e: Exception) {
                _uiState.value = TiendaUiState.Error("No se pudo obtener el saldo")
            }
        }
    }

    private fun actualizarEstadoConSaldo(nuevoSaldo: Int) {
        val estadoActual = _uiState.value
        if (estadoActual is TiendaUiState.Success) {
            _uiState.value = estadoActual.copy(saldo = nuevoSaldo)
        }
    }

    fun fetchProductos() {
        viewModelScope.launch {
            _uiState.value = TiendaUiState.Loading
            try {
                val lista = cf.getProductosCase()
                val saldo = cf.getSaldoCase()
                _uiState.value = TiendaUiState.Success(lista, saldo)
            } catch (e: Exception) {
                _uiState.value = TiendaUiState.Error("No se pudo conectar con el servidor")
            }
        }
    }

    fun comprarProducto(producto: Producto) {
        viewModelScope.launch {
            try{
                val exito = cf.comprarProductoCase(producto)
                if (exito) {
                    val saldoActual = cf.getSaldoCase()
                    // Refrescar la lista de productos y el saldo después de una compra exitosa
                    // FIXME quizas se podria hacer q se marcara como comprado el producto en vez de volver a cargar todo
                    fetchProductos()
                }
            } catch (e: Exception) {
                //TODO MIRAR SI MAS
                _uiState.value = TiendaUiState.Error("Error al comprar el producto")
            }

        }
    }
}
