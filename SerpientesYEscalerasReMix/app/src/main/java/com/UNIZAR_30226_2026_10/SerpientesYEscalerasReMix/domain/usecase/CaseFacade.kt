package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import CerrarSesionCase
import IniciarSesionCase
import RegistrarseCase
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.AmigosRepository
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.ConexionRepository
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.JugarContinuarRepository
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.JugarCrearRepository
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.LoginRegisterRepository
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.LogrosRepository
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.PartidaRepository
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.PerfilRepository
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.TiendaRepository

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

    // Lobby / Jugar_Crear
    private val jugarCrearRepository: JugarCrearRepository,

    // Continuar Partida
    private val jugarContinuarRepository: JugarContinuarRepository,

    // Amigos
    private val amigosRepository: AmigosRepository,

    // Tienda
    private val tiendaRepository: TiendaRepository,

    // Partida
    private val partidaRepository: PartidaRepository,

    // Perfil
    private val perfilRepository: PerfilRepository,

    // Logros
    private val logrosRepository: LogrosRepository
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
    val syncLobbyCase = SyncLobbyCase(jugarCrearRepository, partidaRepository, username, lobby)
    val empezarPartidaCase = EmpezarPartidaCase(jugarCrearRepository, partidaRepository)
    val obtenerTablerosCase = ObtenerTablerosCase(jugarCrearRepository)

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
    val continuarPartidaCase = ContinuarPartidaCase(partidaRepository)

    // PERFIL
    val obtenerPerfilCase     = ObtenerPerfilCase(email, perfilRepository)
    val actualizarNombreCase  = ActualizarNombreCase(email, perfilRepository)
    val actualizarSkinCase    = ActualizarSkinCase(email, perfilRepository)
    val obtenerCosmeticosCase = ObtenerCosmeticosCase(email, perfilRepository) // ahora recibe email

    // TIENDA
    val getProductosCase = GetProductosCase(tiendaRepository, email)
    val comprarProductoCase = ComprarProductoCase(tiendaRepository, email)
    val getSaldoCase = GetSaldoCase(tiendaRepository, email)

    // PARTIDA

    // Exposición de flujos del repositorio de Partida
    val tablero = partidaRepository.tablero
    val fichas = partidaRepository.fichas
    val jugadores = partidaRepository.jugadores
    val mano = partidaRepository.mano
    val chat = partidaRepository.chat
    val ganador = partidaRepository.ganador

    // Casos de uso de Partida
    val syncPartidaCase = SyncPartidaCase(partidaRepository, username, matchId)
    val cleanPartidaCase = CleanPartidaCase(partidaRepository)
    val lanzarDadoCase = LanzarDadoCase(partidaRepository, username, matchId)
    val confirmarDestinoCase = ConfirmarDestinoCase(partidaRepository, username, matchId)
    val chatCase = ChatCase(partidaRepository, matchId, username)
    val jugarCartaCase = JugarCartaCase(partidaRepository, username, matchId)

    //Casos de uso de Logros
    val obtenerLogrosCase = ObtenerLogrosCase(email, logrosRepository)
    val reclamarLogroCase = ReclamarLogroCase(email, logrosRepository)

}