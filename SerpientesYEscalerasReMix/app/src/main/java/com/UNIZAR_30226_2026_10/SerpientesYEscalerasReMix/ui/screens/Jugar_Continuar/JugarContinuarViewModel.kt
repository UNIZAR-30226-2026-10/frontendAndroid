package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Jugar_Continuar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CaseFacade
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.Partida
import kotlinx.coroutines.launch

class JugarContinuarViewModel(private val cF: CaseFacade) : ViewModel() {

    companion object {
        fun Factory(cF: CaseFacade): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return JugarContinuarViewModel(cF) as T
            }
        }
    }

    // Estado de la lista de partidas
    var listaPartidas = emptyList<Partida>()

    init {
        viewModelScope.launch {
           listaPartidas = cF.jugarContinuarCase.obtenerPartidas()
        }
    }

    fun continuar(idPartida: Int) { // TODO
        // cF.partidaCase.setId(idPartida)
    }
}