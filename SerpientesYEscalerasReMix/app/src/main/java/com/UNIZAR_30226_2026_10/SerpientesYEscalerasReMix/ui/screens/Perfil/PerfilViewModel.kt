package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Perfil

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CaseFacade
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.CategoriaCosmetico
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.PerfilUsuario
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
        viewModelScope.launch {
            try {
                val mapa = cF.obtenerCosmeticosCase.obtenerTodosLosCosmeticos()
                val sE  = mapa[CategoriaCosmetico.ESCALERA] ?: emptyList()
                val sS  = mapa[CategoriaCosmetico.SERPIENTE] ?: emptyList()
                val sF = mapa[CategoriaCosmetico.FICHA]     ?: emptyList()
                val ic = mapa[CategoriaCosmetico.ICONO]     ?: emptyList()
                skinsEscalera  = sE
                skinsSerpiente = sS
                skinsFicha     = sF
                iconos         = ic
            } catch (e: Exception) {
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
        viewModelScope.launch {
            try {
                val result = cF.actualizarSkinCase(categoria, skinId)
                if (result.isSuccess) {
                    cargarPerfil()
                    cargarCosmeticosDisponibles()
                } else {
                    errorMessage = "Error: ${result.exceptionOrNull()?.message}"
                }
            } catch (e: Exception) {
                errorMessage = e.message
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