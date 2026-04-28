package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository

interface ConexionRepository {
    suspend fun pruebaConexionAPI(): Boolean
}