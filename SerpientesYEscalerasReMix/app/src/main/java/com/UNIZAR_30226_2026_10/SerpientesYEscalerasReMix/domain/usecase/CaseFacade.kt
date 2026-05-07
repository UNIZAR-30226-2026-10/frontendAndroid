package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import CerrarSesionCase
import IniciarSesionCase
import RegistrarseCase
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.AmigosRepository
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.ConexionRepository
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.JugarContinuarRepository
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
    private val partidaRepository: PartidaRepository,

    // Amigos
    private val amigosRepository: AmigosRepository,

    // Continuar Partida
    private val jugarContinuarRepository: JugarContinuarRepository
) {

    // --- GENERAL STATE ---
    // estado compartido entre muchos usecases

    val email: StateFlow<String> = loginRegisterRepository.email
    val username: StateFlow<String> = loginRegisterRepository.username
    val lobbyId: StateFlow<String> = jugarCrearRepository.lobbyId
    val matchId: StateFlow<String> = partidaRepository.matchId

    // --- USECASE ---

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
    val anadirBotCase = AnadirBotCase(jugarCrearRepository, username)
    val cambiarPreparadoCase = CambiarPreparadoCase(jugarCrearRepository, username)
    val seleccionarMazoCase = SeleccionarMazoCase(jugarCrearRepository, username)
    val seleccionarTableroCase = SeleccionarTableroCase(jugarCrearRepository, username)
    val abandonarExpulsarCase = AbandonarExpulsarCase(jugarCrearRepository, username)
    val syncLobbyCase = SyncLobbyCase(jugarCrearRepository, username)
    val empezarPartidaCase = EmpezarPartidaCase(jugarCrearRepository, partidaRepository)

    // AMIGOS

    // Exposición de flujos del repositorio de Amigos
    val amigos = amigosRepository.amigos

    // Casos de uso de Amigos
    val obtenerAmigosCase = ObtenerAmigosCase(amigosRepository, email)
    val anadirAmigoCase = AnadirAmigoCase(amigosRepository, email)
    val eliminarAmigoCase = EliminarAmigoCase(amigosRepository, email)
    val obtenerInvitacionesCase = ObtenerInvitacionesCase(amigosRepository, username)
    val invitarAmigoLobbyCase = InvitarAmigoLobbyCase(amigosRepository, username, lobbyId)
    val responderInvitacionCase = ResponderInvitacionCase(amigosRepository, jugarCrearRepository, username)

    // JUGAR CONTINUAR
    val obtenerRegistroPartidasCase = ObtenerRegistroPartidasCase(jugarContinuarRepository, email)

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
