package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Jugar_Continuar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.RegistroPartida
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CaseFacade
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class JugarContinuarViewModel(private val cF: CaseFacade) : ViewModel() {

    companion object {
        fun Factory(cF: CaseFacade): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return JugarContinuarViewModel(cF) as T
            }
        }
    }

    private val _uiState = MutableStateFlow(JugarContinuarUiState())
    val uiState = _uiState.asStateFlow()

    init {
        obtenerListaPartidas()
    }

    private fun obtenerListaPartidas() {
        viewModelScope.launch {
            val partidas = cF.obtenerRegistroPartidasCase.obtenerPartidas()
            _uiState.update { it.copy(listaPartidas = partidas) }
        }
    }

    fun continuar(idPartida: String) {
        viewModelScope.launch {
            // Implementación según la lógica de tu CaseFacade
            // cF.partidaCase.setId(idPartida)
        }
    }
}

data class JugarContinuarUiState(
    val listaPartidas: List<RegistroPartida> = emptyList()
)
