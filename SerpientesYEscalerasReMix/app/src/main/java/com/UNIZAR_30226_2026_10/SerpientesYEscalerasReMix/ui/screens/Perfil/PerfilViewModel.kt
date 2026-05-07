package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Perfil

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
        cargarPerfil()
        cargarCosmeticosDisponibles()
    }

    fun cargarPerfil() {
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
        viewModelScope.launch {
            try {
                skinsEscalera  = cF.obtenerCosmeticosCase.obtenerSkinsEscalera()
                skinsSerpiente = cF.obtenerCosmeticosCase.obtenerSkinsSerpiente()
                skinsFicha     = cF.obtenerCosmeticosCase.obtenerSkinsFicha()
                iconos         = cF.obtenerCosmeticosCase.obtenerIconos()
            } catch (e: Exception) {
                errorMessage = "Error en cosméticos: ${e.message}"
            }
        }
    }

    fun actualizarNombre(nuevoNombre: String) {
        viewModelScope.launch {
            try {
                cF.actualizarNombreCase(nuevoNombre)
                perfil = perfil?.copy(nombre = nuevoNombre)
            } catch (e: Exception) {
                errorMessage = "Error al actualizar nombre: ${e.message}"
            }
        }
    }

    fun actualizarCosmetico(categoria: CategoriaCosmetico, skinId: String) {
        viewModelScope.launch {
            try {
                cF.actualizarSkinCase(categoria, skinId)
                perfil = when (categoria) {
                    CategoriaCosmetico.ESCALERA  -> perfil?.copy(skinEscaleraActual = skinId)
                    CategoriaCosmetico.SERPIENTE -> perfil?.copy(skinSerpienteActual = skinId)
                    CategoriaCosmetico.FICHA     -> perfil?.copy(skinFichaActual = skinId)
                    CategoriaCosmetico.ICONO     -> perfil?.copy(iconoActual = skinId)
                }
            } catch (e: Exception) {
                errorMessage = "Error al actualizar cosmético: ${e.message}"
            }
        }
    }

    fun cerrarSesion(onSucces: () -> Unit) {
        viewModelScope.launch {
            cF.cerrarSesionCase()
            onSucces()
        }
    }
}