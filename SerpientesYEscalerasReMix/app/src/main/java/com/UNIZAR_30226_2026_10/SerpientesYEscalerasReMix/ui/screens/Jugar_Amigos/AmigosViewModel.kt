package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Jugar_Amigos

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Usuario
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CaseFacade
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class AmigosViewModel(private val cF: CaseFacade, private val snackHost: SnackbarHostState) : ViewModel() {

    companion object {
        fun Factory(cF: CaseFacade, snackHost: SnackbarHostState): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    AmigosViewModel(cF, snackHost) as T
            }
    }

    private val _uiState = MutableStateFlow(AmigosUiState())
    val uiState = _uiState.asStateFlow()

    private var pollingJob: Job? = null
    private var pollingMS: Long = 2000

    private fun showErrorSnackbar(message: String) {
        viewModelScope.launch {
            snackHost.showSnackbar(message)
        }
    }

    fun iniciarPolling() {
        if (pollingJob?.isActive == true) return
        pollingJob = viewModelScope.launch {
            while (isActive) {
                try {
                    val nuevosAmigos = cF.obtenerAmigosCase()
                    _uiState.update { it.copy(listaAmigosPolling = nuevosAmigos) }
                } catch (e: Exception) {
                    // Manejo silencioso o error si es necesario
                }
                delay(pollingMS)
            }
        }
    }

    fun detenerPolling() {
        pollingJob?.cancel()
    }

    fun buscarAmigos(searchText: String) {
        _uiState.update { it.copy(searchText = searchText) }
    }

    fun invitarAmigo(nombre: String) {
        viewModelScope.launch {
            cF.invitarAmigoLobbyCase(nombre)
        }
    }

    fun unirseAPartida(amigoNombre: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            // Lógica de unión pendiente de implementar
            onSuccess() 
        }
    }

    fun borrarAmigo(nombre: String) {
        viewModelScope.launch {
            cF.eliminarAmigoCase(nombre)
        }
    }

    fun anadirAmigo(nombre: String) {
        viewModelScope.launch {
            cF.anadirAmigoCase(nombre)
        }
    }
}

data class AmigosUiState(
    val listaAmigosPolling: List<Usuario> = emptyList(),
    val searchText: String = ""
) {
    val listaAmigosMostrada: List<Usuario>
        get() = if (searchText.isBlank()) {
            listaAmigosPolling
        } else {
            listaAmigosPolling.filter { it.nombre.contains(searchText, ignoreCase = true) }
        }
}
