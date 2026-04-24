package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Partida

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.fakes.fakeFichasSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.fakes.fakeJugadoresSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.fakes.fakeManoCartas
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.fakes.fakeTableroSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Carta
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.FichaSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.JugadoresSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Movimiento
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.MsgChat
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.TableroSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CaseFacade
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class PartidaViewModel(private val cF: CaseFacade) : ViewModel() {

    private val _uiState = MutableStateFlow(PartidaUiState())
    val uiState = _uiState.asStateFlow()

    // Funciones POLLING

    private var pollingJob: Job? = null
    private var pollingMS: Long = 2000 // Consultar cada 2 segundos

    fun iniciarPolling() {
        if (pollingJob?.isActive == true) return

        pollingJob = viewModelScope.launch {
            while (isActive) {
                cF.syncPartidaCase()
                delay(pollingMS)
            }
        }
    }

    fun detenerPolling() {
        pollingJob?.cancel()
    }


    // Funciones AUXILIARES

    private fun coordinarDialogos(diag: String) {
        _uiState.update {
            it.copy(
                mostrarDialogoCarta = diag == "Carta",
                mostrarDialogoChat = diag == "Chat",
                mostrarDialogoEscalera = diag == "Escalera",
            )
        }
    }

    // Funciones MOVER FICHAS

    fun onLanzarDado() {
        viewModelScope.launch {
            val dadoPair = cF.moverFichaCase()
            val puntuacionDado = dadoPair.first
            val casillasPosibles = dadoPair.second

            // Mostrar resultado
            _uiState.update {
                it.copy(
                    mostrarDialogoPuntuacion = true,
                    puntuacionDado = puntuacionDado
                )
            }

            delay(1000)

            // Eleccion de ficha
            _uiState.update {
                it.copy(
                    mostrarDialogoPuntuacion = false,
                    mostrarDialogoIndicacion = true,
                    indicacion = "Seleccione una ficha a mover",

                    seleccionFichas = true,
                    casillasAElegir = casillasPosibles
                )
            }
        }
    }

    // Función llamada tras tirar el dado y elegir una ficha que mover
    fun onSeleccionFicha(fichaId: Int) {

        // Eleccion de casilla
        _uiState.update {
            it.copy(
                mostrarDialogoIndicacion = true,
                indicacion = "Seleccione una casilla",
                seleccionFichas = false,

                // Mostrar solo las casillas desde la ficha
                casillasAElegir = cF.moverFichaCase.filtrarMovimientosPorFicha(it.casillasAElegir, fichaId),
                seleccionCasilla = true
            )
        }
    }

    // Función llamada tras elegir una ficha que mover, para elegir el destino
    // o al elegir una bifucación
    fun onSeleccionCasilla(casillaId: Int, esBifurcacion: Boolean) {
        // si es bifurcacion llamar de nuevo para doble eleccion
        // si es escalera mostrar el dialogo correspondiente

        if (!esBifurcacion) {
            _uiState.update {
                it.copy(
                    mostrarDialogoIndicacion = false,
                    indicacion = "",
                    seleccionCasilla = false
                )

                // TODO llamada usecase
            }
        } else {
            // TODO llamada usecase

            _uiState.update {
                it.copy( //TODO
                    mostrarDialogoIndicacion = true,
                    indicacion = "Seleccione una casilla",
                    seleccionFichas = false,

                    casillasAElegir = emptyList(), // TODO
                    seleccionCasilla = true
                )
            }
        }
    }

    fun onCerrarDialogoEscalera() {
        coordinarDialogos("")
    }

    fun onSubirEscalera() { // Todo añadir params necesarios
        // Todo enviar al backend decision (Siempre que llegues aqui no te quedan movs)
    }

    // Funciones CHAT

    fun mostrarChat() {
        viewModelScope.launch {
            // Todo llamada a chat
            coordinarDialogos("Chat")
        }
    }

    fun cerrarChat() {
        coordinarDialogos("")
    }

    fun enviarMsgChat(msg: String) {
        viewModelScope.launch {
            // TODO
            // Actualizar _uiState con el nuevo TableroSnapshot/FichasSnapshot
        }
    }

    // Funciones JUGAR CARTA

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

    fun onSeleccionJugadorCarta(email: String) {
        viewModelScope.launch {
            // TODO
        }
    }

    fun onSeleccionCasillaCarta(casillaId: Int) {
        viewModelScope.launch {
            // TODO
        }
    }
    fun onSeleccionFichaCarta(fichaId: Int) {
        viewModelScope.launch {
            // TODO
        }
    }
}

data class PartidaUiState( // Todo cambiar por lineas comentadas (esta asi para poder usar preview)
    // Contenido dinamico
    val tablero: TableroSnapshot = fakeTableroSnapshot, // TableroSnapshot(emptyList()),
    val fichas: List<FichaSnapshot> = fakeFichasSnapshot, // emptyList(),
    val jugadores: JugadoresSnapshot = fakeJugadoresSnapshot, // JugadoresSnapshot(0, 0, emptyList()),
    val mano: MutableList<Carta?> = fakeManoCartas, // mutableListOf(),
    val cartaEnDetalle: Carta? = null,
    val esMiTurno: Boolean = false,
    val yaJugadoCarta: Boolean = false,
    val chat: List<MsgChat> = emptyList(),

    // Fases de la partida
    val seleccionFichas: Boolean = false,
    val seleccionCasilla: Boolean = false,
    val casillasAElegir: List<Movimiento> = emptyList(),

    // Control de dialogos
    val mostrarDialogoCarta: Boolean = false,
    val mostrarDialogoChat: Boolean = false,

    val mostrarDialogoEscalera: Boolean = false,
    val casillaEscalera: Int = 0,

    val mostrarDialogoIndicacion: Boolean = false,
    val indicacion: String = "",

    val mostrarDialogoPuntuacion: Boolean = false,
    val puntuacionDado: Int = 0,

    // Control de cartas
    val seleccionJugadorCarta: Boolean = false,
    val seleccionFichaCarta: Boolean = false,
    val seleccionCasillaCarta: Boolean = false
)
