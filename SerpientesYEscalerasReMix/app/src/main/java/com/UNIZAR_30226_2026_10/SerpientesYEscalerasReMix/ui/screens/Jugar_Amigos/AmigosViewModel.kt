package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Jugar_Amigos

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CaseFacade
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.Usuario
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

// TODO añadir loop para hacer polling de la lista (llegan invitaciones, se añaden amigos, etc)
// IMPORTANTE SOLO HACER EL POLLING CUANDO NO SE ESTEN BUSCANDO USUARIOS
class AmigosViewModel(private val cF: CaseFacade) : ViewModel() {

    // Para que solo exista un único viewmodel durante toda la ejecución de la app
    companion object {
        fun Factory(cF: CaseFacade): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AmigosViewModel(cF) as T
                }
            }
    }

    private var listaAmigosOriginal = mutableStateOf(emptyList<Usuario>())
    private var listaAmigosBuscada = mutableStateOf(emptyList<Usuario>())

    // Atributo publico que es mostrado en la pantalla, con redefinición de su getter
    val listaAmigosMostrada: List<Usuario>
        get() = if (estaBuscando) listaAmigosBuscada.value else listaAmigosOriginal.value

    private var estaBuscando = false

    // Referencia al hilo de polling
    private var pollingJob: Job? = null

    fun iniciarPollingAmigos() {
        if (pollingJob?.isActive == true) return

        pollingJob = viewModelScope.launch {
            while (isActive) {
                if (!estaBuscando) {
                    Log.d("POLLING", "hola")
                    try {
                        val nuevosAmigos = cF.amigosCase.obtenerAmigos()
                        listaAmigosOriginal.value = nuevosAmigos
                    } catch (e: Exception) {
                        // Opcional: manejar error de red
                    }
                }
                delay(2000) // Espera 2 segundos
            }
        }
    }

    fun detenerPollingAmigos() {
        pollingJob?.cancel()
    }

    fun buscarAmigos(searchText: String) {
        viewModelScope.launch {
            if (searchText.isBlank()) {
                estaBuscando = false
                listaAmigosBuscada.value = emptyList()
            } else {
                estaBuscando = true
                // Llamada al caso de uso
                val resultado = cF.amigosCase.buscarAmigos(searchText)
                listaAmigosBuscada.value = resultado
            }
        }
    }

    fun invitarAmigo(nombre: String) {
        viewModelScope.launch {
            cF.amigosCase.invitarAmigoLobby(nombre)
        }
    }

    fun unirseAPartida(nombre: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val exito = cF.amigosCase.unirseAmigoLobby(nombre)
            if (exito) {
                onSuccess()
            }
        }
    }

    fun borrarAmigo(nombre: String) {
        viewModelScope.launch {
            cF.amigosCase.eliminarAmigo(nombre)
        }
    }

    fun anadirAmigo(nombre: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            cF.amigosCase.añadirAmigo(nombre)
            onSuccess()
        }
    }
}