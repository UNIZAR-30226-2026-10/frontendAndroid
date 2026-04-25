package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Partida

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Carta
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.FichaSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.JugadoresSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Movimiento
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.MsgChat
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.TableroSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.TipoCasilla
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CaseFacade
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class PartidaViewModel(private val cF: CaseFacade) : ViewModel() {

    companion object {
        fun Factory(cF: CaseFacade): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PartidaViewModel(cF) as T
                }
            }
    }


    private val _uiState = MutableStateFlow(PartidaUiState())
    val uiState = _uiState.asStateFlow()

    init {
        // Conexión de Flows del Repository a UI State
        viewModelScope.launch {
            launch { cF.tablero.collect { data -> _uiState.update { it.copy(tablero = data) } } }
            launch { cF.fichas.collect { data -> _uiState.update { it.copy(fichas = data) } } }
            launch {
                cF.jugadores.collect { data ->
                    _uiState.update {
                        it.copy(
                            jugadores = data,
                            esMiTurno = data.jugadores.getOrNull(data.turno)?.email == cF.email.value
                        )
                    }
                }
            }
            launch { cF.mano.collect { data -> _uiState.update { it.copy(mano = data.toMutableList()) } } }
            launch { cF.chat.collect { data -> _uiState.update { it.copy(chat = data) } } }
        }
    }

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
                mostrarDialogoBifurcacion = diag == "Bifurcacion"
            )
        }
    }

    // Funciones MOVER FICHAS

    fun onLanzarDado() {
        viewModelScope.launch {
            val dadoPair = cF.lanzarDadoCase()
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
        viewModelScope.launch {
            // Eleccion de casilla
            _uiState.update {
                it.copy(
                    mostrarDialogoIndicacion = true,
                    indicacion = "Seleccione una casilla",
                    seleccionFichas = false,

                    fichaSeleccionada = fichaId,

                    // Mostrar solo las casillas desde la ficha
                    casillasAElegir = cF.lanzarDadoCase(it.casillasAElegir, fichaId),
                    seleccionCasilla = true
                )
            }
        }
    }

    // Función llamada tras elegir una ficha que mover, para elegir el destino
    // o al elegir una bifucación
    fun onSeleccionCasilla(casillaId: Int) {
        // si es bifurcacion o escalera mostrar dialogo correspondiente

        viewModelScope.launch {
            // Obtener movimiento correspondiente
            val movDestino = _uiState.value.casillasAElegir.find { it.casillaId == casillaId }!!

            if (_uiState.value.tablero.casillas[casillaId].tipo == TipoCasilla.Escalera) {
                _uiState.update {
                    it.copy(
                        casillaEscaleraIni = casillaId,
                        casillaEscaleraFin = _uiState.value.tablero.casillas[casillaId].saltoA!!
                    )
                }
                coordinarDialogos("Escalera")
            } else if (movDestino.esBifurcacion && movDestino.pasosRestantes > 0) {
                _uiState.update {
                    it.copy(
                        casillaBifurcacionIni = casillaId,
                        casillaA = _uiState.value.tablero.casillas[casillaId].siguientes[0],
                        casillaB = _uiState.value.tablero.casillas[casillaId].siguientes[1]
                    )
                }
                coordinarDialogos("Bifurcacion")
            } else {
                _uiState.update {
                    it.copy(
                        mostrarDialogoIndicacion = false,
                        indicacion = "",
                        seleccionCasilla = false,

                        casillasAElegir = emptyList(),
                    )
                }

                cF.confirmarDestinoCase(movDestino)
            }
        }
    }

    fun onCerrarDialogoEscalera() {

        viewModelScope.launch {
            val casillaIniAux = _uiState.value.casillaEscaleraIni
            _uiState.update {
                it.copy(
                    mostrarDialogoIndicacion = false,
                    indicacion = "",
                    seleccionCasilla = false,

                    casillaEscaleraIni = 0,
                    casillaEscaleraFin = 0,

                    casillasAElegir = emptyList(),
                )
            }

            coordinarDialogos("")

            val movDestino = _uiState.value.casillasAElegir.find { it.casillaId == casillaIniAux }!!

            cF.confirmarDestinoCase(movDestino)
        }
    }

    fun onSubirEscalera() {

        viewModelScope.launch {
            val movAux = Movimiento(
                fichaId = _uiState.value.fichaSeleccionada,
                casillaId = _uiState.value.casillaEscaleraFin,
                // Siempre que llegues aqui no te quedan movs
                esBifurcacion = false,
                pasosRestantes = 0
            )

            _uiState.update {
                it.copy(
                    mostrarDialogoIndicacion = false,
                    indicacion = "",
                    seleccionCasilla = false,

                    casillaEscaleraIni = 0,
                    casillaEscaleraFin = 0,

                    casillasAElegir = emptyList(),
                )
            }

            coordinarDialogos("")

            cF.confirmarDestinoCase(movAux)
        }
    }

    fun onSeleccionBifurcacion(casillaId: Int) {

        viewModelScope.launch {
            val anteriorCasilla = _uiState.value.casillaBifurcacionIni
            _uiState.update {
                it.copy(
                    mostrarDialogoIndicacion = false,
                    indicacion = "",
                    seleccionCasilla = false,

                    casillaBifurcacionIni = 0,
                    casillaA = 0,
                    casillaB = 0,

                    casillasAElegir = emptyList(),
                )
            }

            coordinarDialogos("")

            val movDestino = _uiState.value.casillasAElegir.find { it.casillaId == anteriorCasilla }!!
            cF.confirmarDestinoCase(movDestino, casillaId)
        }
    }

    // Funciones CHAT

    fun mostrarChat() {
        viewModelScope.launch {
            cF.chatCase.recibir()
            coordinarDialogos("Chat")
        }
    }

    fun cerrarChat() = coordinarDialogos("")

    fun enviarMsgChat(msg: String) {
        viewModelScope.launch {
            val mensaje = MsgChat(sender = cF.username.value, message = msg)
            cF.chatCase.enviar(mensaje)
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
        _uiState.update { it.copy(cartaJugada = carta) }

        when (carta.nombre) {
            "Wild Frank", "Carpintero" -> {
                _uiState.update {
                    it.copy(
                        seleccionCasillaCarta = true,

                        casillasAElegirCarta = it.tablero.casillas.mapIndexedNotNull { index, casilla ->
                            if (casilla.tipo != TipoCasilla.Vacio && index != 0 && index != 99) index + 1 else null
                        },

                        mostrarDialogoIndicacion = true,
                        indicacion = "Seleccione casilla de INICIO"
                    )
                }
            }

            "Mal de ojo", "Pickpocket", "Dado envenenado", "Serpiente en tu bota", "Bolsillo roto", "Noqueo" -> {
                _uiState.update {
                    it.copy(
                        seleccionJugadorCarta = true,

                        mostrarDialogoIndicacion = true,
                        indicacion = "Seleccione un jugador objetivo"
                    )
                }
            }

            "Robo de identidad" -> {
                _uiState.update {
                    it.copy(
                        seleccionFichaCarta = true,

                        mostrarDialogoIndicacion = true,
                        indicacion = "Seleccione una de sus fichas"
                    )
                }
            }

            else -> { // Cartas sin objetivo (Ej: Dado dorado, Antidoto, Exceso de medios)
                ejecutarUsoCarta()
                coordinarDialogos("")
            }
        }

    }

    fun onSeleccionJugadorCarta(emailObjetivo: String) {
        ejecutarUsoCarta(target = emailObjetivo)
        _uiState.update {
            it.copy(
                seleccionJugadorCarta = false,
                mostrarDialogoIndicacion = false,
                indicacion = ""
            )
        }
    }

    fun onSeleccionFichaCarta(fichaId: Int) {
        // Para "Robo de identidad", 'who' es el ID de la ficha según el backend
        ejecutarUsoCarta(target = fichaId.toString())
        _uiState.update {
            it.copy(
                mostrarDialogoIndicacion = false,
                indicacion = "",

                seleccionFichaCarta = false
            )
        }
    }

    fun onSeleccionCasillaCarta(casillaId: Int) {

        if (_uiState.value.casillaCartaIni == null) { // Primera selección (Inicio)
            val cartaNombre = _uiState.value.cartaJugada?.nombre ?: return

            val casillasAux =
                if (cartaNombre == "Wild Frank") _uiState.value.casillasAElegirCarta.filter { it > casillaId }
                else _uiState.value.casillasAElegirCarta.filter { it < casillaId }

            _uiState.update {
                it.copy(
                    casillaCartaIni = casillaId,
                    indicacion = "Seleccione casilla de FIN",

                    casillasAElegirCarta = casillasAux
                )
            }
        } else { // Segunda selección (Fin) y ejecución
            ejecutarUsoCarta(inicio = _uiState.value.casillaCartaIni!!, fin = casillaId)
            _uiState.update {
                it.copy(
                    mostrarDialogoIndicacion = false,
                    indicacion = "",

                    seleccionCasillaCarta = false,
                    casillaCartaIni = null
                )
            }
        }
    }

    private fun ejecutarUsoCarta(
        target: String? = null,
        inicio: Int? = null,
        fin: Int? = null
    ) {
        val cartaId = _uiState.value.cartaJugada?.nombre ?: return

        viewModelScope.launch {
            cF.jugarCartaCase(cartaId, target, inicio, fin)
            _uiState.update { it.copy(cartaEnDetalle = null, mostrarDialogoIndicacion = false) }
        }
    }
}

data class PartidaUiState(
    // Todo cambiar por lineas comentadas (esta asi para poder usar preview)
    // Contenido dinamico
    val tablero: TableroSnapshot = TableroSnapshot(emptyList()),
    val fichas: List<FichaSnapshot> = emptyList(),
    val jugadores: JugadoresSnapshot = JugadoresSnapshot(0, 0, emptyList()),
    val mano: MutableList<Carta?> = mutableListOf(),
    val cartaEnDetalle: Carta? = null,
    val esMiTurno: Boolean = false,
    val yaJugadoCarta: Boolean = false,
    val chat: List<MsgChat> = emptyList(),

    // Fases de la partida
    val seleccionFichas: Boolean = false,
    val fichaSeleccionada: Int = 0,
    val seleccionCasilla: Boolean = false,
    val casillasAElegir: List<Movimiento> = emptyList(),

    // Control de dialogos
    val mostrarDialogoCarta: Boolean = false,
    val mostrarDialogoChat: Boolean = false,

    val mostrarDialogoEscalera: Boolean = false,
    val casillaEscaleraIni: Int = 0,
    val casillaEscaleraFin: Int = 0,

    val mostrarDialogoBifurcacion: Boolean = false,
    val casillaBifurcacionIni: Int = 0,
    val casillaA: Int = 0,
    val casillaB: Int = 0,


    val mostrarDialogoPuntuacion: Boolean = false,
    val puntuacionDado: Int = 0,

    val mostrarDialogoIndicacion: Boolean = false,
    val indicacion: String = "",

    // Control de cartas
    val cartaJugada: Carta? = null,

    val seleccionJugadorCarta: Boolean = false,
    val seleccionFichaCarta: Boolean = false,

    val seleccionCasillaCarta: Boolean = false,
    val casillasAElegirCarta: List<Int> = emptyList(),
    val casillaCartaIni: Int? = null
)
