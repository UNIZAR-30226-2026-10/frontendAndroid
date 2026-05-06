package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Logros

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CaseFacade
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.LogroUsuario
import kotlinx.coroutines.launch

class LogrosViewModel(private val cF: CaseFacade) : ViewModel() {

    companion object {
        fun Factory(cF: CaseFacade): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return LogrosViewModel(cF) as T
                }
            }
    }

    var logros by mutableStateOf<List<LogroUsuario>>(emptyList())
        private set

    var cargando by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        cargarLogros()
    }

    fun cargarLogros() {
        viewModelScope.launch {
            cargando = true
            errorMessage = null
            try {
                // Invocación al caso de uso de obtención
                logros = cF.obtenerLogrosCase()
            } catch (e: Exception) {
                errorMessage = "Error al cargar logros: ${e.message}"
            } finally {
                cargando = false
            }
        }
    }

    fun reclamarLogro(achievementId: String) {
        viewModelScope.launch {
            try {
                // Invocación al caso de uso de reclamar (POST API)
                cF.reclamarLogroCase(achievementId)
                logros = logros.map { logro ->
                    if (logro.id == achievementId) logro.copy(recompensaReclamada = true)
                    else logro
                }
            } catch (e: Exception) {
                errorMessage = "Error al reclamar: ${e.message}"
            }
        }
    }
}