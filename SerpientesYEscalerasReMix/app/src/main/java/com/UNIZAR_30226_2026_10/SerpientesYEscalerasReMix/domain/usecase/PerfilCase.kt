package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import kotlinx.coroutines.flow.StateFlow

data class PerfilUsuario(
    val nombre: String,
    val victorias: Int,
    val derrotas: Int,
    val monedas: Int,
    val iconoActual: String,
    val skinEscaleraActual: String,
    val skinSerpienteActual: String,
    val skinFichaActual: String
)

enum class CategoriaCosmetico { ESCALERA, SERPIENTE, FICHA, ICONO }

class PerfilCase(
    private val email: StateFlow<String>,
    private val username: StateFlow<String>
) {

    suspend fun obtenerPerfil(): PerfilUsuario {
        // TODO llamada a la API: GET /api/users/:email/profile
        return PerfilUsuario(
            nombre              = username.value.ifBlank { "SerpienteGanadora5" },
            victorias           = 35,
            derrotas            = 12,
            monedas             = 500,
            iconoActual         = "icono_default",
            skinEscaleraActual  = "escalera_default",
            skinSerpienteActual = "serpiente_default",
            skinFichaActual     = "ficha_default"
        )
    }

    suspend fun actualizarNombre(nuevoNombre: String) {
        // TODO llamada a la API: PUT /api/users/:email/username
        // Body: "username"
    }

    suspend fun actualizarSkinEscalera(skinId: String) {
        // TODO llamada a la API: PUT /api/users/:email/stair
        // Body: "stair"
    }

    suspend fun actualizarSkinSerpiente(skinId: String) {
        // TODO llamada a la API: endpoint pendiente de añadir en la API
    }

    suspend fun actualizarSkinFicha(skinId: String) {
        // TODO llamada a la API: PUT /api/users/:email/pawn
        // Body: "pawn"
    }

    suspend fun actualizarIcono(iconId: String) {
        // TODO llamada a la API: PUT /api/users/:email/icon
        // Body: "icon"
    }

    suspend fun obtenerSkinsEscalera(): List<String> {
        // TODO llamada a la API: GET /api/users/:email/stairs
        return listOf("escalera_default", "escalera_dorada", "escalera_madera")
    }

    suspend fun obtenerSkinsSerpiente(): List<String> {
        // TODO llamada a la API: endpoint pendiente de añadir en la API
        return listOf("serpiente_default", "serpiente_dorada", "serpiente_pixel")
    }

    suspend fun obtenerSkinsFicha(): List<String> {
        // TODO llamada a la API: GET /api/users/:email/pawns
        return listOf("ficha_default", "ficha_dorada", "ficha_madera")
    }

    suspend fun obtenerIconos(): List<String> {
        // TODO llamada a la API: GET /api/users/:email/icons
        return listOf("icono_default", "icono_serpiente", "icono_escalera")
    }
}