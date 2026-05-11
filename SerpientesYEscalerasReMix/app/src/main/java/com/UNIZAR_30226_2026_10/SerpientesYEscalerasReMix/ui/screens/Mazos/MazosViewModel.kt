package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Mazos

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.fakes.carta1
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.fakes.carta2
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.fakes.carta3
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.fakes.carta4
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.fakes.carta5
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.fakes.listaDeMazosDePrueba
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.fakes.mazoVacio
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Carta
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Mazo
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Producto
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CaseFacade
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Tienda.TiendaUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MazosViewModel(private val cF: CaseFacade) : ViewModel() {

    // Estado privado
    private val _uiState = MutableStateFlow<MazosUiState>(MazosUiState.Loading)
    // Estado público inmutable
    val uiState: StateFlow<MazosUiState> = _uiState

    companion object {
        fun Factory(cF: CaseFacade): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(MazosViewModel::class.java)) {
                        return MazosViewModel(cF) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            }
    }

    var mazos by mutableStateOf(listaDeMazosDePrueba)
        private set

    var mazoSeleccionado by mutableStateOf(mazos.firstOrNull() ?: mazoVacio)
        private set

    var cartasDisponibles by mutableStateOf(listaCartasDisponibles())
        private set

    init {
        viewModelScope.launch {
            cF.email.collectLatest { email ->
                if (email.isNotBlank()) {
                    fetchMazos()
                }
            }
        }
    }

    suspend fun fetchMazos() {
        if (cF.email.value.isBlank()) {
            return
        }
        _uiState.value = MazosUiState.Loading
        try {
            val mazos = try {
                cF.obtenerMazosCase()
            } catch (e: Exception) {
                Log.e("TiendaViewModel", "Error al obtener mazos, usando lista de mazos vacía: ${e.message}")
                emptyList<Mazo>()
            }
            this.mazos = mazos
            mazoSeleccionado = mazos.firstOrNull() ?: mazoVacio
            _uiState.value = MazosUiState.Success(mazos)
        } catch (e: Exception) {
            _uiState.value = MazosUiState.Error("No se pudo conectar con el servidor")
        }
    }


    // Como ya tenemos la lista de mazos no necesitamos pedirselo a la API, podemos seleccionar por
    // indice en la lista que tenemos, aunque de hecho cuando trabajamos con una mazo ya estamos en el
    // asi que no es necesario ni siquiera el indice, se puede seleccionar directamente por el mazo
    fun seleccionarMazoPorNumero(mazo: Int?) {
        if (mazo == null) {
            mazoSeleccionado = mazos.firstOrNull() ?: mazoVacio
            return
        }

        mazos.getOrNull(mazo) ?: mazoVacio
    }



    fun seleccionarMazo(mazo: Mazo) {
        mazoSeleccionado = mazo
    }

    fun actualizarNombreMazo(nombre: String) {
        mazoSeleccionado = mazoSeleccionado.copy(nombre = nombre)
        actualizarMazoEnLista(mazoSeleccionado)
    }

    fun anadirCarta(carta: Carta) {
        if (mazoSeleccionado.cartas.size >= 10) return
        val nuevasCartas = mazoSeleccionado.cartas + carta
        mazoSeleccionado = mazoSeleccionado.copy(cartas = nuevasCartas)
        actualizarMazoEnLista(mazoSeleccionado)
    }

    fun eliminarCarta(indice: Int) {
        if (indice !in mazoSeleccionado.cartas.indices) return
        val nuevasCartas = mazoSeleccionado.cartas.toMutableList().also { it.removeAt(indice) }
        mazoSeleccionado = mazoSeleccionado.copy(cartas = nuevasCartas)
        actualizarMazoEnLista(mazoSeleccionado)
    }

    fun crearNuevoMazo() {
        if (mazos.size >= 8) return
        val nuevoMazo = mazoVacio.copy(nombre = "Mazo ${mazos.size + 1}")
        mazos = mazos + nuevoMazo
        mazoSeleccionado = nuevoMazo
    }

    fun eliminarMazoSeleccionado() {
        if (mazos.isEmpty()) {
            mazoSeleccionado = mazoVacio
            return
        }

        mazos = mazos.filterNot { it == mazoSeleccionado }
        mazoSeleccionado = mazos.firstOrNull() ?: mazoVacio
    }

    private fun actualizarMazoEnLista(mazo: Mazo) {
        mazos = mazos.map { if (it == mazoSeleccionado) mazo else it }
    }

    private fun listaCartasDisponibles(): List<Carta> {
        return listOf(
            carta1, carta2, carta3, carta4, carta5,
            carta1, carta2, carta3, carta4, carta5
        )
    }
}
