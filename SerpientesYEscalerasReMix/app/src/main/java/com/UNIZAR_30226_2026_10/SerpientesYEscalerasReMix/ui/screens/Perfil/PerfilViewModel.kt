package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Perfil

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CaseFacade
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CategoriaCosmetico
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.PerfilUsuario
import kotlinx.coroutines.launch

class PerfilViewModel(val cF: CaseFacade) : ViewModel() {

    companion object {
        fun Factory(cF: CaseFacade): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PerfilViewModel(cF) as T
                }
            }
    }

    var perfil by mutableStateOf<PerfilUsuario?>(null)
        private set

    var cargando by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var skinsEscalera by mutableStateOf<List<String>>(emptyList())
        private set

    var skinsSerpiente by mutableStateOf<List<String>>(emptyList())
        private set

    var skinsFicha by mutableStateOf<List<String>>(emptyList())
        private set

    var iconos by mutableStateOf<List<String>>(emptyList())
        private set

    init {
        viewModelScope.launch {
            cF.email.collect { email: String ->
                if (email.isNotEmpty()) {
                    cargarPerfil()
                    cargarCosmeticosDisponibles()
                }
            }
        }
    }

    fun cargarPerfil() {
        // FIX: guardia para evitar llamada de red si el email aún no está listo
        if (cF.email.value.isEmpty()) return
        viewModelScope.launch {
            cargando = true
            errorMessage = null
            try {
                perfil = cF.obtenerPerfilCase()
            } catch (e: Exception) {
                errorMessage = "Error al cargar perfil: ${e.message}"
            } finally {
                cargando = false
            }
        }
    }

    private fun cargarCosmeticosDisponibles() {
        // FIX: una sola llamada que devuelve el mapa completo (4 peticiones en paralelo)
        // en lugar de 4 llamadas individuales que internamente hacían 4 peticiones cada una (16 total)
        viewModelScope.launch {
            try {
                val mapa = cF.obtenerCosmeticosCase.obtenerTodosLosCosmeticos()
                val sE = mapa[CategoriaCosmetico.ESCALERA]  ?: emptyList()
                val sS = mapa[CategoriaCosmetico.SERPIENTE] ?: emptyList()
                val sF = mapa[CategoriaCosmetico.FICHA]     ?: emptyList()
                val ic = mapa[CategoriaCosmetico.ICONO]     ?: emptyList()
                println("DEBUG: Escaleras recibidas: ${sE.size}")
                // Sobreescribimos siempre para reflejar el estado real del servidor,
                // incluso si viene vacío (el usuario no tiene cosméticos de esa categoría)
                skinsEscalera  = sE
                skinsSerpiente = sS
                skinsFicha     = sF
                iconos         = ic
            } catch (e: Exception) {
                // FIX: no sobreescribimos errorMessage si el perfil ya cargó bien,
                // para no bloquear la pantalla por un fallo secundario de cosméticos
                if (perfil == null) {
                    errorMessage = "Error al cargar cosméticos: ${e.message}"
                }
            }
        }
    }

    fun actualizarNombre(nuevoNombre: String) {
        viewModelScope.launch {
            try {
                val result = cF.actualizarNombreCase(nuevoNombre)
                if (result.isSuccess) {
                    perfil = perfil?.copy(nombre = nuevoNombre)
                } else {
                    errorMessage = "Error al actualizar nombre"
                }
            } catch (e: Exception) {
                errorMessage = "Error al actualizar nombre: ${e.message}"
            }
        }
    }

    fun actualizarCosmetico(categoria: CategoriaCosmetico, skinId: String) {
        println("DEBUG: Intentando actualizar $categoria a $skinId") // Traza 1
        viewModelScope.launch {
            try {
                val result = cF.actualizarSkinCase(categoria, skinId)
                println("DEBUG: Resultado de la API: ${result.isSuccess}") // Traza 2

                if (result.isSuccess) {
                    if (perfil == null) println("DEBUG: El perfil es NULO, no puedo actualizar la UI")
                    cargarPerfil()
                    val perfilActual = perfil ?: return@launch
                    // ... resto del código
                    println("DEBUG: UI actualizada localmente a $skinId")
                } else {
                    println("DEBUG: Error API: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                println("DEBUG: Excepción lanzada: ${e.message}")
            }
        }
    }

    fun actualizarIcono(iconId: String) {
        actualizarCosmetico(CategoriaCosmetico.ICONO, iconId)
    }

    fun cerrarSesion(context: Context, onSucces: () -> Unit) {
        viewModelScope.launch {
            cF.cerrarSesionCase(context)
            onSucces()
        }
    }
}