package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import android.content.Context
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.local.LocalStorage
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.ConexionRepository
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.PartidaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CaseFacade(
    context: Context, // TODO eliminar de aqui

    // Repositorios
    // TODO ir añadiendo aqui las interfaces que se vayan creando, fuera seran instanciadas como toquen
    private val pruebaConexionRepository: ConexionRepository,
    
    private val partidaRepository: PartidaRepository
) {

    // TODO Cambiar e iniciar esto en MainActivity junto con remote, luego cerceriorarse que todo se crea bien con su repo, etc
    // Creación del almacen local
    private val local = LocalStorage(context)

    // estado compartido entre usecases TODO eliminar de aqui y recuperar del repository correspondiente
    private val _email = MutableStateFlow("YO@gmail.com") // TODO cambiar y enlazar con repo o repos
    val email: StateFlow<String> = _email.asStateFlow()

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _lobbyId = MutableStateFlow("")
    val lobbyId: StateFlow<String> = _lobbyId.asStateFlow()

    private val _matchId = MutableStateFlow("1") // TODO cambiar y enlazar con repo o repos
    val matchId: StateFlow<String> = _matchId.asStateFlow()
    

    // Crear Todos los casos de uso, asignando local y remoteApi segun corresponda
    val loginRegisterCase = LoginRegisterCase(local, _email, _username)

    val amigosCase = AmigosCase(email, username, _lobbyId)

    val jugarContinuarCase = JugarContinuarCase(email, username)

    val jugarCrearCase = JugarCrearCase(email, username, _lobbyId)
    
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
    
    // TEST/LOG
    
    // Caso de uso de prueba ping con API/Retrofit
    val pruebaConexionCase = PruebaConexionCase(pruebaConexionRepository)
}