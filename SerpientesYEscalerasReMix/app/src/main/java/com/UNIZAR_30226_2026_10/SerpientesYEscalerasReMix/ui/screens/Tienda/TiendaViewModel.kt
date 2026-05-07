package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Tienda

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Producto
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
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
        viewModelScope.launch {
            cf.email.collectLatest { email ->
                if (email.isNotBlank()) {
                    fetchProductos()
                }
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
            if (cf.email.value.isBlank()) {
                return@launch
            }
            _uiState.value = TiendaUiState.Loading
            try {
                val lista = try {
                    cf.getProductosCase()
                } catch (e: Exception) {
                    Log.e("TiendaViewModel", "Error al obtener productos, usando lista vacía: ${e.message}")
                    emptyList<Producto>()
                }
                val saldo = try {
                    cf.getSaldoCase()
                } catch (e: Exception) {
                    obtenerSaldoActual() ?: 0
                    Log.e("TiendaViewModel", "Error al obtener el saldo, usando saldo actual: ${obtenerSaldoActual()}")
                }
                _uiState.value = TiendaUiState.Success(lista, saldo)
            } catch (e: Exception) {
                _uiState.value = TiendaUiState.Error("No se pudo conectar con el servidor")
            }
        }
    }

    fun comprarProducto(producto: Producto) {
        viewModelScope.launch {
            try{
                if (cf.email.value.isBlank()) {
                    _uiState.value = TiendaUiState.Error("Usuario no logeado")
                    return@launch
                }
                val exito = cf.comprarProductoCase(producto)
                if (exito) {
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

    private fun obtenerSaldoActual(): Int? {
        val estado = _uiState.value
        return if (estado is TiendaUiState.Success) estado.saldo else null
    }
}
