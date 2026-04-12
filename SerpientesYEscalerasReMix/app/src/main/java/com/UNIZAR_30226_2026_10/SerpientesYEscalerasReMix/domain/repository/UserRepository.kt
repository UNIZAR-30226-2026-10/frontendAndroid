package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository

import kotlinx.coroutines.flow.StateFlow


interface UserRepository {
    val saldo: StateFlow<Int>
    fun actualizarSaldo(nuevoSaldo: Int)
    suspend fun fetchSaldoReal(): Int // Pedir el saldo real al backend
}