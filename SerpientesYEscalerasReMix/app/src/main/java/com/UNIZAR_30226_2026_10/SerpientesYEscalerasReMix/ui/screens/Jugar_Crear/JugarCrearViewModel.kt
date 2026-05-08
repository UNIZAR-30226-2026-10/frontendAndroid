package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Jugar_Crear

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Lobby
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CaseFacade
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class JugarCrearViewModel(private val cF: CaseFacade) : ViewModel() {

    companion object {
        fun Factory(cF: CaseFacade): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return JugarCrearViewModel(cF) as T
                }
            }
    }

    private val _uiState = MutableStateFlow(JugarCrearUiState())

    val uiState = _uiState.asStateFlow()

    private val _seleccionMazo = MutableStateFlow("")
    val seleccionMazo = _seleccionMazo.asStateFlow()

    init {
        // Conexión de Flows del Repository a UI State
        viewModelScope.launch {
            launch {
                cF.lobby.collect { data ->
                    _uiState.update { it.copy(lobby = data) }
                    data.players.find { it?.username == _uiState.value.username }?.deckName?.let { mazo ->
                        _seleccionMazo.value = mazo
                    }
                }
            }
            launch { cF.username.collect { data -> _uiState.update { it.copy(username = data) } } }
        }
        obtenerTableros()
    }

    // Funciones POLLING

    private var pollingMS: Long = 2000 // Consultar cada 2 segundos
    private var pollingJob: Job? = null

    fun iniciarPolling(onPartidaIniciada: () -> Unit) {
        if (pollingJob?.isActive == true) return

        pollingJob = viewModelScope.launch {
            while (isActive) {
                cF.syncLobbyCase(onPartidaIniciada)
                if (_uiState.value.lobby != null) {
                    _uiState.update { it.copy(vistaLider = _uiState.value.lobby!!.hostUsername == _uiState.value.username) }
                }
                delay(pollingMS)
            }
        }
    }

    fun detenerPolling() {
        pollingJob?.cancel()
    }

    fun obtenerTableros() {
        viewModelScope.launch {
            val boards = cF.obtenerTablerosCase()
            _uiState.update { it.copy(nombreTableros = boards) }
        }
    }

    // Métodos de interacción con el Lobby
    fun onCambiarListo(listo: Boolean) {
        viewModelScope.launch {
            cF.cambiarPreparadoCase(listo)
        }
    }

    fun onSeleccionarMazo(nombreMazo: String) {
        viewModelScope.launch {
            cF.seleccionarMazoCase(nombreMazo)
            _seleccionMazo.value = nombreMazo
        }
    }

    fun onSeleccionarTablero(nombreTablero: String) {
        viewModelScope.launch {
            cF.seleccionarTableroCase(nombreTablero)
            _uiState.update { it.copy(seleccionTablero = nombreTablero) }
        }
    }

    fun onAnadirBot() {
        viewModelScope.launch {
            cF.anadirBotCase()
        }
    }

    fun onAbandonar() {
        viewModelScope.launch {
            cF.abandonarExpulsarCase(cF.username.value)
        }
    }

    fun onExpulsar(idx: Int) {
        viewModelScope.launch {
            val usernameAEliminar = _uiState.value.lobby?.players?.getOrNull(idx)?.username ?: ""
            if (usernameAEliminar.isNotEmpty()) {
                cF.abandonarExpulsarCase(usernameAEliminar)
            }
        }
    }

    fun onEmpezarPartida(onSucces: () -> Unit) {
        viewModelScope.launch {
            val lobbyId = _uiState.value.lobby?.id ?: ""
            cF.cambiarPreparadoCase(true)
            cF.empezarPartidaCase(lobbyId, onSucces)
        }
    }
}

data class JugarCrearUiState(
    val lobby: Lobby? = null,
    val vistaLider: Boolean = false,
    val username: String = "",
    val seleccionTablero: String = "",
    val nombreTableros: List<String> = emptyList()
)
