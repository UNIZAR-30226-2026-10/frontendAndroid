package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import android.content.Context
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.local.LocalStorage

class CaseFacade(context: Context) {

    // Creación del almacen local
    private val local = LocalStorage(context)

    // TODO Creación de la clase remote relacionada con la API y añadirla en los constructores
    // private val remoteApi =

    // Crear Todos los casos de uso, asignando local y remoteApi segun corresponda
    public val loginRegisterCase = LoginRegisterCase(local)

    public val amigosCase = AmigosCase(loginRegisterCase.emailFlow)

    public val jugarContinuarCase = JugarContinuarCase(loginRegisterCase.emailFlow)
}