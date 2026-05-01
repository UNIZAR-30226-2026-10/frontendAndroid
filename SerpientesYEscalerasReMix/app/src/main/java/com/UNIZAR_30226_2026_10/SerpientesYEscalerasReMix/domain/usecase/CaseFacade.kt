package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import CerrarSesionCase
import IniciarSesionCase
import RegistrarseCase
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.ConexionRepository
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.JugarCrearRepository
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.LoginRegisterRepository
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.PartidaRepository
import kotlinx.coroutines.flow.StateFlow

class CaseFacade(
    // Repositorios
    // TODO ir añadiendo aqui las interfaces que se vayan creando, fuera seran instanciadas como toquen

    // Prueba Inicial Retrofit
    private val pruebaConexionRepository: ConexionRepository,

    // Login / Registro
    private val loginRegisterRepository: LoginRegisterRepository,

    // Lobby / Jugar_Crear
    private val jugarCrearRepository: JugarCrearRepository,

    // Partida
    private val partidaRepository: PartidaRepository
) {

    // --- GENERAL STATE ---
    // estado compartido entre muchos usecases

    val email: StateFlow<String> = loginRegisterRepository.email
    val username: StateFlow<String> = loginRegisterRepository.username

    val matchId: StateFlow<String> = partidaRepository.matchId

    // --- USECASE ---

    val amigosCase = AmigosCase(email, username) // TODO eleminar

    val jugarContinuarCase = JugarContinuarCase(email, username) // TODO eleminar

    // TEST/LOG

    // Caso de uso de prueba ping con API/Retrofit
    val pruebaConexionCase = PruebaConexionCase(pruebaConexionRepository)

    // LOGIN/REGISTER
    val comprobarLoginCase = ComprobarLoginCase(loginRegisterRepository)
    val inciarSesionCase = IniciarSesionCase(loginRegisterRepository)
    val registrarseCase = RegistrarseCase(loginRegisterRepository)
    val cerrarSesionCase = CerrarSesionCase(loginRegisterRepository)

    // JUGAR CREAR

    // Exposición de flujos del repositorio de Jugar Crear
    val lobby = jugarCrearRepository.lobbyActual

    // Casos de uso de Jugar Crear
    val anadirBotCase = AnadirBotCase(jugarCrearRepository, email)
    val cambiarPreparadoCase = CambiarPreparadoCase(jugarCrearRepository, email)
    val seleccionarMazoCase = SeleccionarMazoCase(jugarCrearRepository, email)
    val seleccionarTableroCase = SeleccionarTableroCase(jugarCrearRepository, email)
    val abandonarExpulsarCase = AbandonarExpulsarCase(jugarCrearRepository, email, username)
    val syncLobbyCase = SyncLobbyCase(jugarCrearRepository, email, username)
    val empezarPartidaCase = EmpezarPartidaCase(jugarCrearRepository, partidaRepository)

    // PARTIDA

    // Exposición de flujos del repositorio de Partida
    val tablero = partidaRepository.tablero
    val fichas = partidaRepository.fichas
    val jugadores = partidaRepository.jugadores
    val mano = partidaRepository.mano
    val chat = partidaRepository.chat

    // Casos de uso de Partida
    val syncPartidaCase = SyncPartidaCase(partidaRepository, email, matchId)
    val lanzarDadoCase = LanzarDadoCase(partidaRepository, email, matchId)
    val confirmarDestinoCase = ConfirmarDestinoCase(partidaRepository, email, matchId)
    val chatCase = ChatCase(partidaRepository, matchId)
    val jugarCartaCase = JugarCartaCase(partidaRepository, email, matchId)
}