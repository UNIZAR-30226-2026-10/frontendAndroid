package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import android.content.Context
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.local.LocalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CaseFacade(context: Context) {

    // Creación del almacen local
    private val local = LocalStorage(context)

    // TODO Creación de la clase remote relacionada con la API y añadirla en los constructores
    // private val remoteApi =

    // estado compartido entre usecases
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _lobbyId = MutableStateFlow("")
    val lobbyId: StateFlow<String> = _lobbyId.asStateFlow()


    // Crear Todos los casos de uso, asignando local y remoteApi segun corresponda
    public val loginRegisterCase = LoginRegisterCase(local, _email, _username)

    public val amigosCase = AmigosCase(email, username, _lobbyId)

    public val jugarContinuarCase = JugarContinuarCase(email, username)

    public val jugarCrearCase = JugarCrearCase(email, username, _lobbyId)
}