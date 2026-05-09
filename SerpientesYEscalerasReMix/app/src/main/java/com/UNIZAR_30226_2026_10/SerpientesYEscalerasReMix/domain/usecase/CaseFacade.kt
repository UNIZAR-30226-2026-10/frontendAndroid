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
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.PerfilRepository
import kotlinx.coroutines.flow.StateFlow

class CaseFacade(
    private val pruebaConexionRepository: ConexionRepository,
    private val loginRegisterRepository: LoginRegisterRepository,
    private val jugarCrearRepository: JugarCrearRepository,
    private val partidaRepository: PartidaRepository,
    private val amigosRepository: AmigosRepository,
    private val jugarContinuarRepository: JugarContinuarRepository,
    private val perfilRepository: PerfilRepository
) {

    // --- GENERAL STATE ---

    val email: StateFlow<String>    = loginRegisterRepository.email
    val username: StateFlow<String> = loginRegisterRepository.username
    val lobbyId: StateFlow<String>  = jugarCrearRepository.lobbyId
    val matchId: StateFlow<String>  = partidaRepository.matchId

    // --- USECASE ---

    // TEST/LOG
    val pruebaConexionCase = PruebaConexionCase(pruebaConexionRepository)

    // LOGIN/REGISTER
    val comprobarLoginCase = ComprobarLoginCase(loginRegisterRepository)
    val inciarSesionCase   = IniciarSesionCase(loginRegisterRepository)
    val registrarseCase    = RegistrarseCase(loginRegisterRepository)
    val cerrarSesionCase   = CerrarSesionCase(loginRegisterRepository)

    // JUGAR CREAR
    val lobby = jugarCrearRepository.lobbyActual

    val anadirBotCase          = AnadirBotCase(jugarCrearRepository, username)
    val cambiarPreparadoCase   = CambiarPreparadoCase(jugarCrearRepository, username)
    val seleccionarMazoCase    = SeleccionarMazoCase(jugarCrearRepository, username)
    val seleccionarTableroCase = SeleccionarTableroCase(jugarCrearRepository, username)
    val abandonarExpulsarCase  = AbandonarExpulsarCase(jugarCrearRepository, username)
    val syncLobbyCase          = SyncLobbyCase(jugarCrearRepository, username)
    val empezarPartidaCase     = EmpezarPartidaCase(jugarCrearRepository, partidaRepository)

    // AMIGOS
    val amigos = amigosRepository.amigos

    val obtenerAmigosCase       = ObtenerAmigosCase(amigosRepository, email)
    val anadirAmigoCase         = AnadirAmigoCase(amigosRepository, email)
    val eliminarAmigoCase       = EliminarAmigoCase(amigosRepository, email)
    val obtenerInvitacionesCase = ObtenerInvitacionesCase(amigosRepository, username)
    val invitarAmigoLobbyCase   = InvitarAmigoLobbyCase(amigosRepository, username, lobbyId)
    val responderInvitacionCase = ResponderInvitacionCase(amigosRepository, jugarCrearRepository, username)

    // JUGAR CONTINUAR
    val obtenerRegistroPartidasCase = ObtenerRegistroPartidasCase(jugarContinuarRepository, email)

    // PERFIL
    val obtenerPerfilCase     = ObtenerPerfilCase(email, perfilRepository)
    val actualizarNombreCase  = ActualizarNombreCase(email, perfilRepository)
    val actualizarSkinCase    = ActualizarSkinCase(email, perfilRepository)
    val actualizarIconoCase   = ActualizarIconoCase(email, perfilRepository)
    val obtenerCosmeticosCase = ObtenerCosmeticosCase(email, perfilRepository) // ahora recibe email

    // PARTIDA
    val tablero   = partidaRepository.tablero
    val fichas    = partidaRepository.fichas
    val jugadores = partidaRepository.jugadores
    val mano      = partidaRepository.mano
    val chat      = partidaRepository.chat

    val syncPartidaCase      = SyncPartidaCase(partidaRepository, email, matchId)
    val lanzarDadoCase       = LanzarDadoCase(partidaRepository, email, matchId)
    val confirmarDestinoCase = ConfirmarDestinoCase(partidaRepository, email, matchId)
    val chatCase             = ChatCase(partidaRepository, matchId)
    val jugarCartaCase       = JugarCartaCase(partidaRepository, email, matchId)
}