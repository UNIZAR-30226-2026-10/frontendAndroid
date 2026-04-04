package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Jugar_Crear

import android.util.Log
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
    val email: StateFlow<String> = cF.email
    val username: StateFlow<String> = cF.username
    val lobbyId: StateFlow<String> = cF.lobbyId

    private var pollingJob: Job? = null

    // Polling del lobby
    fun iniciarPollingLobby() {
        if (pollingJob?.isActive == true) return

        pollingJob = viewModelScope.launch {
            while (isActive) {
                Log.d("A", username.value)
                Log.d("A", email.value)
                cF.jugarCrearCase.obtenerEstadoLobby()
                delay(2000) // Consultar cada 2 segundos
            }
        }
    }

    fun detenerPollingLobby() {
        pollingJob?.cancel()
    }

    // Métodos para obtener información del usuario respecto al lobby
    fun soyLider(): Boolean {
        return email.value == lobbyActual.value?.hostEmail
    }

    // Métodos de interacción con el Lobby
    fun crearLobby() {
        viewModelScope.launch {
            cF.jugarCrearCase.crearLobby()
        }
    }

    fun cambiarPreparado(preparado: Boolean) { // TODO
        viewModelScope.launch {
            cF.jugarCrearCase.cambiarEstadoPreparado(preparado)
        }
    }

    fun seleccionarMazo(nombreMazo: String) { // TODO
        viewModelScope.launch {
            cF.jugarCrearCase.seleccionarMazo(nombreMazo)
        }
    }

    fun anadirBot() { // TODO quizas añadir un snackbar si alguien se une antes de que se pueda añadir el bot
        viewModelScope.launch {
            cF.jugarCrearCase.anadirBot()
        }
    }

    fun abandonar() {
        viewModelScope.launch {
            cF.jugarCrearCase.abandonarExpulsar(email.value)
            cF.jugarCrearCase.crearLobby()
        }
    }

    fun expulsar(emailAEliminar: String) {
        viewModelScope.launch {
            cF.jugarCrearCase.abandonarExpulsar(emailAEliminar)
        }
    }
}