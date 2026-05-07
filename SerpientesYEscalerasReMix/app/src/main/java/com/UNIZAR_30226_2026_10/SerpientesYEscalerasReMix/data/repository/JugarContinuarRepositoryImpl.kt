package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.repository

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.RegistroPartida
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.JugarContinuarRepository
import kotlinx.coroutines.delay

class JugarContinuarRepositoryImpl : JugarContinuarRepository {
    override suspend fun obtenerPartidas(email: String): List<RegistroPartida> {
        // Simulación de retraso de red
        delay(1000)
        
        // Mocks de ejemplo
        return listOf(
            RegistroPartida("Partida Épica", "2023-10-27 10:30", 5, "Jugador1, Jugador2", 1),
            RegistroPartida("Duelo de Titanes", "2023-10-26 15:45", 12, "Jugador1, Bot1", 2),
            RegistroPartida("Revancha", "2023-10-25 20:15", 2, "Jugador1, Jugador3, Jugador4", 3)
        )
    }
}
