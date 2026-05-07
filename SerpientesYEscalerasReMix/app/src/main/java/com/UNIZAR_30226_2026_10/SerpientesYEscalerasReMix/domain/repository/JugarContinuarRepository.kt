package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.RegistroPartida

interface JugarContinuarRepository {
    suspend fun obtenerPartidas(email: String): List<RegistroPartida>
}
