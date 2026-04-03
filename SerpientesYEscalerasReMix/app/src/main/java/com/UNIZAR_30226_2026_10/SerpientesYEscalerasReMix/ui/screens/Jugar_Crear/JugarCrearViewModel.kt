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

    companion object {
        fun Factory(cF: CaseFacade): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return JugarCrearViewModel(cF) as T
                }
            }
    }

    val lobbyActual: StateFlow<Lobby?> = cF.jugarCrearCase.currentLobby
    val email: StateFlow<String> = cF.userEmail

    private var pollingJob: Job? = null

    // Polling del lobby
    fun iniciarPollingLobby(lobbyId: String) {
        if (pollingJob?.isActive == true) return

        pollingJob = viewModelScope.launch {
            while (isActive) {
                cF.jugarCrearCase.obtenerEstadoLobby(lobbyId)
                delay(2000) // Consultar cada 2 segundos
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

    fun cambiarEstadoListo(estaListo: Boolean) {
        viewModelScope.launch {
            cF.jugarCrearCase.cambiarEstadoListo(lobbyActual.value?.id, estaListo)
        }
    }

    fun seleccionarMazo(nombreMazo: String) {
        viewModelScope.launch {
            cF.jugarCrearCase.seleccionarMazo(lobbyActual.value?.id, nombreMazo)
        }
    }

    fun anadirBot() {
        viewModelScope.launch {
            cF.jugarCrearCase.anadirBot(lobbyActual.value?.id)
        }
    }

    fun abandonarOExpulsar(emailAEliminar: String?) {
        viewModelScope.launch {
            cF.jugarCrearCase.abandonarExpulsar(lobbyActual.value?.id, emailAEliminar)
        }
    }
}