package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

class ObtenerCosmeticosCase {

    suspend fun obtenerSkinsEscalera(): List<String> {
        return listOf("escalera_default", "escalera_dorada", "escalera_madera")
    }

    suspend fun obtenerSkinsSerpiente(): List<String> {
        return listOf("serpiente_default", "serpiente_dorada", "serpiente_pixel")
    }

    suspend fun obtenerSkinsFicha(): List<String> {
        return listOf("ficha_default", "ficha_dorada", "ficha_madera")
    }

    suspend fun obtenerIconos(): List<String> {
        return listOf("icono_default", "icono_serpiente", "icono_escalera")
    }
}