package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.screens.Perfil

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CategoriaCosmetico
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CaseFacade
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.PerfilUsuario
import kotlinx.coroutines.launch

class PerfilViewModel(private val cF: CaseFacade) : ViewModel() {

    // Para que solo exista un único ViewModel durante toda la ejecución de la app
    companion object {
        fun Factory(cF: CaseFacade): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return PerfilViewModel(cF) as T
                }
            }
    }

    // ── Estado observable por la UI ───────────────────────────────────────────

    var perfil by mutableStateOf<PerfilUsuario?>(null)
        private set

    var cargando by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    // Listas de cosméticos disponibles (se cargan al abrir la pantalla)
    var skinsEscalera by mutableStateOf<List<String>>(emptyList())
        private set

    var skinsFicha by mutableStateOf<List<String>>(emptyList())
        private set

    var iconos by mutableStateOf<List<String>>(emptyList())
        private set

    // ── Inicialización ────────────────────────────────────────────────────────

    init {
        cargarPerfil()
        cargarCosmeticosDisponibles()
    }

    // ── Casos de uso ──────────────────────────────────────────────────────────

    /** Carga (o recarga) el perfil desde la capa de datos. */
    fun cargarPerfil() {
        viewModelScope.launch {
            cargando = true
            error    = null
            try {
                perfil = cF.perfilCase.obtenerPerfil()
            } catch (e: Exception) {
                error = "No se pudo cargar el perfil: ${e.message}"
            } finally {
                cargando = false
            }
        }
    }

    /** Carga los cosméticos desbloqueados disponibles para seleccionar. */
    private fun cargarCosmeticosDisponibles() {
        viewModelScope.launch {
            try {
                skinsEscalera = cF.perfilCase.obtenerSkinsEscalera()
                skinsFicha    = cF.perfilCase.obtenerSkinsFicha()
                iconos        = cF.perfilCase.obtenerIconos()
            } catch (e: Exception) {
                // No bloqueante: si falla, las listas quedan vacías
                error = "No se pudieron cargar los cosméticos: ${e.message}"
            }
        }
    }

    /** Persiste el nuevo nombre y actualiza el estado local. */
    fun actualizarNombre(nuevoNombre: String) {
        viewModelScope.launch {
            try {
                cF.perfilCase.actualizarNombre(nuevoNombre)
                perfil = perfil?.copy(nombre = nuevoNombre)
            } catch (e: Exception) {
                error = "No se pudo actualizar el nombre: ${e.message}"
            }
        }
    }

    /** Cambia el cosmético activo de una categoría y actualiza el estado local. */
    fun actualizarCosmetico(categoria: CategoriaCosmetico, skinId: String) {
        viewModelScope.launch {
            try {
                when (categoria) {
                    CategoriaCosmetico.ESCALERA  -> {
                        cF.perfilCase.actualizarSkinEscalera(skinId)
                        perfil = perfil?.copy(skinEscaleraActual = skinId)
                    }
                    CategoriaCosmetico.FICHA     -> {
                        cF.perfilCase.actualizarSkinFicha(skinId)
                        perfil = perfil?.copy(skinFichaActual = skinId)
                    }
                    CategoriaCosmetico.ICONO     -> {
                        cF.perfilCase.actualizarIcono(skinId)
                        perfil = perfil?.copy(iconoActual = skinId)
                    }
                    CategoriaCosmetico.SERPIENTE -> {
                        // TODO: llamar cuando exista el endpoint en la API
                        perfil = perfil?.copy(skinSerpienteActual = skinId)
                    }
                }
            } catch (e: Exception) {
                error = "No se pudo actualizar el cosmético: ${e.message}"
            }
        }
    }
}