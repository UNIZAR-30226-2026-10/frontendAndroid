package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Jugar_Amigos

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CaseFacade
import kotlinx.coroutines.launch

data class Usuario( // TODO mover a donde sea pertienente
    val nombre: String,
    val estadoTexto: String, // "online", "te ha invitado", etc.
    val estaOnline: Boolean,
    val esAmigo: Boolean = false,
    val haInvitado: Boolean = false
)

val listaAmigosEjemplo = listOf(
    Usuario("EscaladorMaestro", "online", true, true),
    Usuario("ZigZagKing", "te ha invitado", true, true, haInvitado = true),
    Usuario("Colmillo Veloz", "online", true, true),
    Usuario("Escalera77", "desconectado", false, true)
)

// TODO añadir loop para hacer polling de la lista (llegan invitaciones, se añaden amigos, etc)
// IMPORTANTE SOLO HACER EL POLLING CUANDO NO SE ESTEN BUSCANDO USUARIOS
class AmigosViewModel(private val cF: CaseFacade) : ViewModel() {

    // Para que solo exista un único viewmodel durante toda la ejecución de la app
    companion object {
        fun Factory(cF: CaseFacade): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AmigosViewModel(cF) as T
            }
        }
    }

    private val listaAmigosOriginal =
        listaAmigosEjemplo // TODO eliminar, deberia de ser inicialmente buscada con la API

    private var listaAmigosBuscada = mutableStateOf(emptyList<Usuario>())

    // Atributo publico que es mostrado en la pantalla, con redefinición de su getter (TODO cambiar getter)
    val listaAmigosMostrada: List<Usuario>
        get() = if (listaAmigosBuscada.value.isEmpty()) listaAmigosOriginal else listaAmigosBuscada.value

    fun buscarAmigos(searchText: String) {
        viewModelScope.launch {
            if (searchText.isBlank()) {
                listaAmigosBuscada.value = emptyList()
            } else {
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