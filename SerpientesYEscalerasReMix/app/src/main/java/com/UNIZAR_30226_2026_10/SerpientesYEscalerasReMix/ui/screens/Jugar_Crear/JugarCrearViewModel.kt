package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Jugar_Crear

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CaseFacade
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.Lobby
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class JugarCrearViewModel(private val cF: CaseFacade) : ViewModel() {

    // Factory para la inyección de dependencias siguiendo tu patrón
    companion object {
        fun Factory(cF: CaseFacade): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return JugarCrearViewModel(cF) as T
                }
            }
    }

    // Exponemos el StateFlow del caso de uso directamente para que la UI lo observe
    val lobbyActual: StateFlow<Lobby?> = cF.jugarCrearCase.currentLobby

    private var pollingJob: Job? = null

    // Lógica de Polling similar a AmigosViewModel
    fun iniciarPollingLobby(lobbyId: String) {
        if (pollingJob?.isActive == true) return

        pollingJob = viewModelScope.launch {
            while (isActive) {
                try {
                    // Actualiza el atributo currentLobby en el caso de uso
                    cF.jugarCrearCase.obtenerEstadoLobby(lobbyId)
                } catch (e: Exception) {
                    // Manejo de errores de red silencioso para el polling
                }
                delay(3000) // Consultar cada 3 segundos
            }
        }
    }

    fun detenerPollingLobby() {
        pollingJob?.cancel()
    }

    // Métodos de interacción con el Lobby

    fun crearLobby(username: String) {
        viewModelScope.launch {
            cF.jugarCrearCase.crearLobby(username)
        }
    }

    fun unirseALobby(lobbyId: String, username: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val exito = cF.jugarCrearCase.unirseALobby(lobbyId, username)
            if (exito) {
                onSuccess()
            }
        }
    }

    fun cambiarEstadoListo(lobbyId: String, estaListo: Boolean) {
        viewModelScope.launch {
            cF.jugarCrearCase.cambiarEstadoListo(lobbyId, estaListo)
        }
    }

    fun seleccionarMazo(lobbyId: String, nombreMazo: String) {
        viewModelScope.launch {
            cF.jugarCrearCase.seleccionarMazo(lobbyId, nombreMazo)
        }
    }

    fun añadirBot(lobbyId: String) {
        viewModelScope.launch {
            cF.jugarCrearCase.añadirBot(lobbyId)
        }
    }

    fun abandonarOExpulsar(lobbyId: String, emailAEliminar: String, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            val exito = cF.jugarCrearCase.abandonarExpulsar(lobbyId, emailAEliminar)
            if (exito) {
                onSuccess()
            }
        }
    }
}