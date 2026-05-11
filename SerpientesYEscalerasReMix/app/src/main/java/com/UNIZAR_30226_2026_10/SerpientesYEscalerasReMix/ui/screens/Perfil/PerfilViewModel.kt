package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Perfil

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
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
            // En tu CaseFacade la variable se llama 'email'
            cF.email.collect { email: String ->
                if (email.isNotEmpty()) {
                    cargarPerfil()
                    cargarCosmeticosDisponibles()
                }
            }
        }
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
                // 1. Usamos la nueva función optimizada que devuelve todo el mapa
                val mapa = cF.obtenerCosmeticosCase.obtenerTodosLosCosmeticos()
                val sE = mapa[CategoriaCosmetico.ESCALERA]  ?: emptyList()
                val sS = mapa[CategoriaCosmetico.SERPIENTE] ?: emptyList()
                val sF = mapa[CategoriaCosmetico.FICHA]     ?: emptyList()
                val ic = mapa[CategoriaCosmetico.ICONO]     ?: emptyList()

                // Solo sobreescribimos si el servidor devuelve algo real
                if (sE.isNotEmpty()) skinsEscalera = sE
                if (sS.isNotEmpty()) skinsSerpiente = sS
                if (sF.isNotEmpty()) skinsFicha = sF
                if (ic.isNotEmpty()) iconos = ic
                // Debug opcional para que veas en consola si llegan datos
                println("DEBUG: Iconos cargados -> ${iconos.size}")

            } catch (e: Exception) {
                errorMessage = "Error al cargar cosméticos: ${e.message}"
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

    fun cerrarSesion(context: Context, onSucces: () -> Unit) {
        viewModelScope.launch {
            cF.cerrarSesionCase(context)
            onSucces()
        }
    }
}