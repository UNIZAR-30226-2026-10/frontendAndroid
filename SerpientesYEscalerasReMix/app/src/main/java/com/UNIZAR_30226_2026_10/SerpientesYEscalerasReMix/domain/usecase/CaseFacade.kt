package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import CerrarSesionCase
import IniciarSesionCase
import RegistrarseCase
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.ConexionRepository
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.LoginRegisterRepository
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.PartidaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CaseFacade(
    // Repositorios
    // TODO ir añadiendo aqui las interfaces que se vayan creando, fuera seran instanciadas como toquen

    // Prueba Inicial Retrofit
    private val pruebaConexionRepository: ConexionRepository,

    // Login / Registro
    private val loginRegisterRepository: LoginRegisterRepository,

    // Partida
    private val partidaRepository: PartidaRepository
) {

    // --- GENERAL STATE ---
    // estado compartido entre muchos usecases

    val email: StateFlow<String> = loginRegisterRepository.email
    val username: StateFlow<String> = loginRegisterRepository.username

    private val _lobbyId = MutableStateFlow("") // TODO cambiar y enlazar con repo o repos
    val lobbyId: StateFlow<String> = _lobbyId.asStateFlow()

    private val _matchId = MutableStateFlow("1") // TODO cambiar y enlazar con repo o repos
    val matchId: StateFlow<String> = _matchId.asStateFlow()

    // --- USECASE ---

    val amigosCase = AmigosCase(email, username, _lobbyId) // TODO eleminar

    val jugarContinuarCase = JugarContinuarCase(email, username) // TODO eleminar

    val jugarCrearCase = JugarCrearCase(email, username, _lobbyId) // TODO eleminar

    // TEST/LOG

    // Caso de uso de prueba ping con API/Retrofit
    val pruebaConexionCase = PruebaConexionCase(pruebaConexionRepository)

    // LOGIN/REGISTER
    val comprobarLoginCase = ComprobarLoginCase(loginRegisterRepository)
    val inciarSesionCase = IniciarSesionCase(loginRegisterRepository)
    val registrarseCase = RegistrarseCase(loginRegisterRepository)
    val cerrarSesionCase = CerrarSesionCase(loginRegisterRepository)

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