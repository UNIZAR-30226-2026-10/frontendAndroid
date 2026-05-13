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
    private val _mazoUiState = MutableStateFlow<MazosUiState>(MazosUiState.Loading)
    private val _editarMazoUiState = MutableStateFlow<EditarMazoUiState>(EditarMazoUiState.Loading)
    // Estado público inmutable
    val mazosUiState: StateFlow<MazosUiState> = _mazoUiState
    val editarMazoUiState: StateFlow<EditarMazoUiState> = _editarMazoUiState

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
                    if (mazos.isEmpty() || mazos == listaDeMazosDePrueba) {
                        fetchMazos()
                    }
                    if (_editarMazoUiState.value is EditarMazoUiState.Loading) {
                        fetchCartasDisponibles()
                    }
                }
            }
        }
    }

    suspend fun fetchMazos() {
        if (cF.email.value.isBlank()) {
            return
        }
        val nombreSeleccionado = mazoSeleccionado.nombre
        _mazoUiState.value = MazosUiState.Loading
        try {
            val mazos = try {
                cF.obtenerMazosCase()
            } catch (e: Exception) {
                Log.e("TiendaViewModel", "Error al obtener mazos, usando lista de mazos vacía: ${e.message}")
                emptyList<Mazo>()
            }
            if (mazos.isEmpty()) {
                this.mazos = emptyList()
                mazoSeleccionado = mazoVacio
                _mazoUiState.value = MazosUiState.Success(emptyList())
                return
            }
            this.mazos = mazos
            mazoSeleccionado = mazos.firstOrNull { it.nombre == nombreSeleccionado }
                ?: mazos.firstOrNull()
                ?: mazoVacio
            _mazoUiState.value = MazosUiState.Success(mazos)
        } catch (e: Exception) {
            _mazoUiState.value = MazosUiState.Error("No se pudo conectar con el servidor")
        }
    }

    suspend fun fetchCartasDisponibles() {
        if (cF.email.value.isBlank()) {
            return
        }
        _editarMazoUiState.value = EditarMazoUiState.Loading
        try {
            val cartas = try {
                cF.obtenerCartasDisponiblesCase()
            } catch (e: Exception) {
                Log.e("MazosViewModel", "Error al obtener cartas disponibles, usando lista de cartas vacía: ${e.message}")
                emptyList<Carta>()
            }
            this.cartasDisponibles = cartas
            val mazoOriginal = mazoSeleccionado.copy(cartas = mazoSeleccionado.cartas.toList())
            _editarMazoUiState.value = EditarMazoUiState.Success(
                mazoOriginal = mazoOriginal,
                mazo = mazoSeleccionado,
                cartasDisponibles = cartasDisponibles,
                esNuevoMazo = mazoSeleccionado == mazoVacio,
                hasChanges = false,
                saveSuccess = false,
                saving = false
            )
            Log.d("MazosViewModel", "fetchCartasDisponibles -> esNuevoMazo=${mazoSeleccionado == mazoVacio} mazo=${mazoSeleccionado.nombre}")
        } catch (e: Exception) {
            _editarMazoUiState.value = EditarMazoUiState.Error("No se pudo conectar con el servidor")
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

    fun actualizarNombreMazoSeleccionado(nombre: String) {
        mazoSeleccionado = mazoSeleccionado.copy(nombre = nombre)
        actualizarMazoEnLista(mazoSeleccionado)
        marcarCambios()
    }

    fun fijarMazoOriginalActual() {
        val current = _editarMazoUiState.value
        if (current is EditarMazoUiState.Success) {
            val original = mazoSeleccionado.copy(cartas = mazoSeleccionado.cartas.toList())
            _editarMazoUiState.value = current.copy(
                mazoOriginal = original,
                mazo = mazoSeleccionado,
                esNuevoMazo = current.esNuevoMazo,
                hasChanges = false,
                saveSuccess = false,
                saving = false
            )
            Log.d("MazosViewModel", "fijarMazoOriginalActual -> esNuevoMazo=${mazoSeleccionado == mazoVacio} mazo=${mazoSeleccionado.nombre}")
        }
    }

    fun cancelarEdicion(mazoOriginal: Mazo) {
        mazoSeleccionado = mazoOriginal.copy(cartas = mazoOriginal.cartas.toList())
        actualizarMazoEnLista(mazoSeleccionado)
        val current = _editarMazoUiState.value
        if (current is EditarMazoUiState.Success) {
            _editarMazoUiState.value = current.copy(
                mazoOriginal = mazoSeleccionado,
                mazo = mazoSeleccionado,
                esNuevoMazo = current.esNuevoMazo,
                hasChanges = false,
                saveSuccess = false,
                saving = false
            )
            Log.d("MazosViewModel", "cancelarEdicion -> esNuevoMazo=${mazoSeleccionado == mazoVacio} mazo=${mazoSeleccionado.nombre}")
        }
    }

    fun resetEditarMazoState() {
        viewModelScope.launch {
            fetchMazos()
            fetchCartasDisponibles()
        }
    }

    fun anadirCartaAMazoSeleccionado(carta: Carta) {
        if (mazoSeleccionado.cartas.size >= 10) return
        val cartasIguales = mazoSeleccionado.cartas.count { it.nombre == carta.nombre }
        if (cartasIguales >= 2) return
        val nuevasCartas = mazoSeleccionado.cartas + carta
        mazoSeleccionado = mazoSeleccionado.copy(cartas = nuevasCartas)
        actualizarMazoEnLista(mazoSeleccionado)
        marcarCambios()
    }

    fun eliminarCartaAMazoSeleccionado(indice: Int) {
        if (indice !in mazoSeleccionado.cartas.indices) return
        val nuevasCartas = mazoSeleccionado.cartas.toMutableList().also { it.removeAt(indice) }
        mazoSeleccionado = mazoSeleccionado.copy(cartas = nuevasCartas)
        actualizarMazoEnLista(mazoSeleccionado)
        marcarCambios()
    }

    fun crearNuevoMazo() {
        if (mazos.size >= 8) return
        val nuevoMazo = mazoVacio.copy(nombre = "Mazo ${mazos.size + 1}")
        mazos = mazos + nuevoMazo
        mazoSeleccionado = nuevoMazo
        Log.d("MazosViewModel", "crearNuevoMazo -> nombre=${nuevoMazo.nombre} cartas=${nuevoMazo.cartas.size}")
        val current = _editarMazoUiState.value
        if (current is EditarMazoUiState.Success) {
            _editarMazoUiState.value = current.copy(
                mazoOriginal = nuevoMazo.copy(cartas = nuevoMazo.cartas.toList()),
                mazo = nuevoMazo,
                esNuevoMazo = true,
                hasChanges = false,
                saveSuccess = false,
                saving = false
            )
            Log.d("MazosViewModel", "crearNuevoMazo -> set esNuevoMazo=true")
        }
    }

    fun eliminarMazoSeleccionado(onResult: (Boolean) -> Unit) {
        if (mazos.isEmpty()) {
            mazoSeleccionado = mazoVacio
            return
        }
        viewModelScope.launch {
            try {
                if (cF.email.value.isBlank()) {
                    onResult(false)
                    return@launch
                }
                val eliminado = cF.eliminarMazoCase(mazoSeleccionado.nombre)
                if (eliminado) {
                    mazos = mazos.filterNot { it == mazoSeleccionado }
                    mazoSeleccionado = mazos.firstOrNull() ?: mazoVacio
                    _mazoUiState.value = MazosUiState.Success(mazos)
                    onResult(true)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    fun comprobarMazoEditable(mazo: Mazo, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                if (cF.email.value.isBlank()) {
                    onResult(false)
                    return@launch
                }
                val exito = cF.editarMazoCase(mazo.nombre, mazo.nombre, mazo.cartas)
                onResult(exito)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    fun guardarCambios(mazoAntiguo: Mazo, mazoNuevo: Mazo) {
        viewModelScope.launch {
            try{
                if (cF.email.value.isBlank()) {
                    _editarMazoUiState.value = EditarMazoUiState.Error("Usuario no ha iniciado sesión")
                    return@launch
                }
                Log.d("MazosViewModel", "Guardando cambios del mazo: ${mazoAntiguo.nombre} -> ${mazoNuevo.nombre}")
                actualizarEstadoGuardando(true)
                val esNuevo = (_editarMazoUiState.value as? EditarMazoUiState.Success)?.esNuevoMazo == true
                Log.d("MazosViewModel", "guardarCambios -> esNuevoMazo=$esNuevo")
                val exito = if (esNuevo) {
                    Log.d("MazosViewModel", "guardarCambios -> creando mazo")
                    cF.crearMazoCase(mazoNuevo)
                } else {
                    Log.d("MazosViewModel", "guardarCambios -> editando mazo")
                    cF.editarMazoCase(mazoAntiguo.nombre, mazoNuevo.nombre, mazoNuevo.cartas)
                }
                Log.d("MazosViewModel", "Resultado de editarMazoCase: $exito")
                if (exito) {
                    mazoSeleccionado = mazoNuevo.copy(cartas = mazoNuevo.cartas.toList())
                    actualizarMazoEnListaPorNombre(mazoAntiguo.nombre, mazoSeleccionado)
                    actualizarEstadoGuardado()
                    if (esNuevo) {
                        fetchMazos()
                    }
                }
            } catch (e: Exception) {
                _editarMazoUiState.value = EditarMazoUiState.Error("Error al guardar los cambios del mazo")
            }

        }
    }

    private fun marcarCambios() {
        val current = _editarMazoUiState.value
        if (current is EditarMazoUiState.Success) {
            _editarMazoUiState.value = current.copy(
                hasChanges = true,
                saveSuccess = false,
                saving = false
            )
        }
    }

    private fun actualizarEstadoGuardando(guardando: Boolean) {
        val current = _editarMazoUiState.value
        if (current is EditarMazoUiState.Success) {
            _editarMazoUiState.value = current.copy(
                mazoOriginal = current.mazoOriginal.copy(
                    cartas = current.mazoOriginal.cartas.toList()
                ),
                saving = guardando
            )
        }
    }

    private fun actualizarEstadoGuardado() {
        val current = _editarMazoUiState.value
        if (current is EditarMazoUiState.Success) {
            _editarMazoUiState.value = current.copy(
                mazoOriginal = current.mazoOriginal.copy(
                    cartas = current.mazoOriginal.cartas.toList()
                ),
                hasChanges = false,
                saveSuccess = true,
                saving = false
            )
        }
    }
    private fun actualizarMazoEnLista(mazo: Mazo) {
        mazos = mazos.map { if (it == mazoSeleccionado) mazo else it }
    }

    private fun actualizarMazoEnListaPorNombre(nombreAnterior: String, mazo: Mazo) {
        mazos = mazos.map { actual ->
            if (actual.nombre == nombreAnterior) {
                mazo
            } else {
                actual
            }
        }
    }


    // Para pruebas
    private fun listaCartasDisponibles(): List<Carta> {
        return listOf(
            carta1, carta2, carta3, carta4, carta5,
            carta1, carta2, carta3, carta4, carta5
        )
    }
}
