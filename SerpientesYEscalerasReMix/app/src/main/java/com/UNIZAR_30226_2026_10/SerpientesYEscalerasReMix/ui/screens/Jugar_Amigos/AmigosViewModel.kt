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
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AmigosViewModel(cF, snackHost) as T
                }
            }
    }

    private val _uiState = MutableStateFlow(AmigosUiState())
    val uiState = _uiState.asStateFlow()

    init {
        // Conexión de Flows del Repository a UI State
        viewModelScope.launch {
            launch { cF.amigos.collect { data -> _uiState.update { it.copy(amigos = data) } } }
        }
    }

    // Funciones POLLING

    private var pollingMS: Long = 2000 // Consultar cada 2 segundos
    private var pollingJob: Job? = null

    fun iniciarPolling() {
        if (pollingJob?.isActive == true) return

        pollingJob = viewModelScope.launch {
            while (isActive) {
                try {
                    cF.obtenerAmigosCase()
                    cF.obtenerInvitacionesCase()
                } catch (e: Exception) {
                    // Manejo silencioso de errores de red en polling
                }
                delay(pollingMS)
            }
        }
    }

    fun detenerPolling() {
        pollingJob?.cancel()
    }

    private fun showErrorSnackbar(message: String) {
        viewModelScope.launch {
            snackHost.showSnackbar(message)
        }
    }

    // Métodos de interacción

    fun buscarAmigos(searchText: String) {
        _uiState.update { it.copy(searchText = searchText) }
    }

    fun invitarAmigo(nombre: String) {
        viewModelScope.launch {
            val exito = cF.invitarAmigoLobbyCase(nombre)
            if (!exito) {
                showErrorSnackbar("No se pudo invitar a $nombre")
            }
        }
    }

    fun responderInvitacion(lobbyId: String, inviteFrom: String, aceptar: Boolean) {
        viewModelScope.launch {
            cF.responderInvitacionCase(lobbyId, inviteFrom, aceptar)
        }
    }

    fun borrarAmigo(nombre: String) {
        viewModelScope.launch {
            val exito = cF.eliminarAmigoCase(nombre)
            if (!exito) {
                showErrorSnackbar("No se pudo eliminar a $nombre")
            }
        }
    }

    fun anadirAmigo(nombre: String) {
        viewModelScope.launch {
            val exito = cF.anadirAmigoCase(nombre)
            if (!exito) {
                showErrorSnackbar("No se pudo añadir a $nombre")
            }
        }
    }

    fun unirseAPartida(lobbyId: String, amigoNombre: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            cF.responderInvitacionCase.invoke(lobbyId, amigoNombre, true)
            onSuccess()
        }
    }
}

data class AmigosUiState(
    val amigos: List<Usuario> = emptyList(),
    val searchText: String = ""
) {
    val listaAmigosMostrada: List<Usuario>
        get() = if (searchText.isBlank()) {
            amigos
        } else {
            amigos.filter { it.nombre.startsWith(searchText, ignoreCase = true) }
        }
}
