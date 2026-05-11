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


class TiendaViewModel (private val cF: CaseFacade) : ViewModel() {

    // Estado privado
    private val _uiState = MutableStateFlow<TiendaUiState>(TiendaUiState.Loading)

    // Estado público inmutable
    val uiState: StateFlow<TiendaUiState> = _uiState

    companion object {
        fun factory(cF: CaseFacade): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(TiendaViewModel::class.java)) {
                        return TiendaViewModel(cF) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        }
    }

    init {
        viewModelScope.launch {
            cF.email.collectLatest { email ->
                if (email.isNotBlank()) {
                    fetchProductos()
                }
            }
        }
    }

    suspend fun fetchProductos() {
        if (cF.email.value.isBlank()) {
            return
        }
        _uiState.value = TiendaUiState.Loading
        try {
            val lista = try {
                cF.getProductosCase()
            } catch (e: Exception) {
                Log.e("TiendaViewModel", "Error al obtener productos, usando lista vacía: ${e.message}")
                emptyList<Producto>()
            }
            val saldo = try {
                cF.getSaldoCase()
            } catch (e: Exception) {
                val fallback = obtenerSaldoActual() ?: 0
                Log.e("TiendaViewModel", "Error al obtener el saldo, usando saldo actual: $fallback")
                fallback
            }
            _uiState.value = TiendaUiState.Success(lista, saldo)
        } catch (e: Exception) {
            _uiState.value = TiendaUiState.Error("No se pudo conectar con el servidor")
        }
    }

    fun comprarProducto(producto: Producto) {
        viewModelScope.launch {
            try{
                if (cF.email.value.isBlank()) {
                    _uiState.value = TiendaUiState.Error("Usuario no ha iniciado sesión")
                    return@launch
                }
                val exito = cF.comprarProductoCase(producto)
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
