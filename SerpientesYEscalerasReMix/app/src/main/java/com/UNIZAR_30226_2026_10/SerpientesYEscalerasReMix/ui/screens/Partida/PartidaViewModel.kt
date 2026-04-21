package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Partida

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Carta
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.FichaSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.JugadoresSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.MsgChat
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.TableroSnapshot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PartidaViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(PartidaUiState())
    val uiState = _uiState.asStateFlow()

    private fun coordinarDialogos(diag: String) {
        _uiState.update {
            it.copy(
                mostrarDialogoCarta = diag == "Carta",
                mostrarDialogoChat = diag == "Chat",
                mostrarDialogoEscalera = diag == "Escalera"
            )
        }
    }
    
    fun lanzarDado() {
        viewModelScope.launch {
            // Todo
            // Actualizar _uiState con el nuevo TableroSnapshot/FichasSnapshot
        }
    }
    
    fun mostrarChat() {
        viewModelScope.launch {
            coordinarDialogos("Chat")
        }
    }

    fun cerrarChat() {
        viewModelScope.launch {
            coordinarDialogos("")
        }
    }

    fun enviarMsgChat(msg: String) {
        viewModelScope.launch {
            // TODO
            // Actualizar _uiState con el nuevo TableroSnapshot/FichasSnapshot
        }
    }

    fun onCartaSeleccionada(carta: Carta) {
        _uiState.update { it.copy(cartaEnDetalle = carta) }
        coordinarDialogos("Carta")
    }

    fun onCerrarDetalleCarta() {
        coordinarDialogos("")
    }

    fun onJugarCarta(carta: Carta) {
        viewModelScope.launch {
            // TODO
            coordinarDialogos("")
        }
    }
}

data class PartidaUiState(
    // Contenido dinamico
    val tablero: TableroSnapshot = TableroSnapshot(emptyList()),
    val fichas: List<FichaSnapshot> = emptyList(),
    val jugadores: JugadoresSnapshot = JugadoresSnapshot(0, 0, emptyList()),
    val mano: MutableList<Carta?> = mutableListOf(),
    val cartaEnDetalle: Carta? = null,
    val esMiTurno: Boolean = false,
    val yaJugadoCarta: Boolean = false,
    val chat: List<MsgChat> = emptyList(),

    // Control de dialogos
    val mostrarDialogoCarta: Boolean = false,
    val mostrarDialogoChat: Boolean = false,
    val mostrarDialogoEscalera: Boolean = false
)
