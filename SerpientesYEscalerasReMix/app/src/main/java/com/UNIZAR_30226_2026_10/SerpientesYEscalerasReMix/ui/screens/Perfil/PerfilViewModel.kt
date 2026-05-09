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
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    PerfilViewModel(cF) as T
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
                errorMessage = "Error al cargar cosméticos: ${e.message}"
            }
        }
    }

    fun actualizarNombre(nuevoNombre: String) {
        viewModelScope.launch {
            try {
                val result: Result<Unit> = cF.actualizarNombreCase(nuevoNombre)
                if (result.isSuccess) {
                    perfil = perfil?.copy(nombre = nuevoNombre)
                } else {
                    errorMessage = "Error al actualizar nombre: ${result.exceptionOrNull()?.message}"
                }
            } catch (e: Exception) {
                errorMessage = "Error al actualizar nombre: ${e.message}"
            }
        }
    }

    fun actualizarCosmetico(categoria: CategoriaCosmetico, skinId: String) {
        viewModelScope.launch {
            try {
                val result: Result<Unit> = cF.actualizarSkinCase(categoria, skinId)
                if (result.isSuccess) {
                    val perfilActual = perfil ?: return@launch
                    perfil = when (categoria) {
                        CategoriaCosmetico.ESCALERA  -> perfilActual.copy(skinEscaleraActual  = skinId)
                        CategoriaCosmetico.SERPIENTE -> perfilActual.copy(skinSerpienteActual = skinId)
                        CategoriaCosmetico.FICHA     -> perfilActual.copy(skinFichaActual     = skinId)
                        CategoriaCosmetico.ICONO     -> perfilActual.copy(iconoActual         = skinId)
                    }
                } else {
                    errorMessage = "Error al actualizar cosmético: ${result.exceptionOrNull()?.message}"
                }
            } catch (e: Exception) {
                errorMessage = "Error al actualizar cosmético: ${e.message}"
            }
        }
    }

    // El icono se actualiza a través del mismo actualizarSkinCase con categoría ICONO,
    // ya que el endpoint PUT /users/{email}/cosmetics cubre todos los tipos de cosmético.
    fun actualizarIcono(iconId: String) {
        actualizarCosmetico(CategoriaCosmetico.ICONO, iconId)
    }

    fun cerrarSesion(onSucces: () -> Unit) {
        viewModelScope.launch {
            cF.cerrarSesionCase()
            onSucces()
        }
    }
}