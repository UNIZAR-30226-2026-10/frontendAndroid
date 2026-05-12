package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Logros

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CaseFacade
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.LogroUsuario
import kotlinx.coroutines.flow.first
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

    // FIX: estado separado para el error de reclamar, para no mezclar con el error
    // de carga inicial (que bloquea toda la pantalla en LogrosScreen)
    var errorReclamar by mutableStateOf<String?>(null)
        private set

    init {
        // FIX: esperamos a que el email esté listo antes de cargar,
        // igual que en PerfilViewModel
        viewModelScope.launch {
            viewModelScope.launch {
                cF.email.first { it.isNotEmpty() }.let {
                    cargarLogros()
                }
            }
        }
    }

    fun cargarLogros() {
        // FIX: guardia para evitar llamada con email vacío si se llama manualmente
        if (cF.email.value.isEmpty()) return
        viewModelScope.launch {
            cargando = true
            errorMessage = null
            try {
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
            errorReclamar = null
            try {
                // 1. Llamada al caso de uso para persistir en el servidor
                cF.reclamarLogroCase(achievementId)

                // 2. Actualizamos el estado local de la lista de logros de forma optimista
                logros = logros.map { logro ->
                    if (logro.id == achievementId) {
                        logro.copy(recompensaReclamada = true)
                    } else {
                        logro
                    }
                }

                // 3. FIX: Forzamos la actualización de las estadísticas globales (SEP)
                // Esto hará que el contador de monedas se refresque en toda la app
                try {
                    cargarLogros() // O la función equivalente que actualice el StateFlow de SEP
                } catch (e: Exception) {
                    // Error silencioso: el logro se reclamó, pero falló la recarga visual de monedas
                    println("Error al refrescar SEP: ${e.message}")
                }

            } catch (e: Exception) {
                // Usamos errorReclamar para no bloquear la pantalla entera
                errorReclamar = "Error al reclamar: ${e.message}"
            }
        }
    }

    fun limpiarErrorReclamar() {
        errorReclamar = null
    }
}