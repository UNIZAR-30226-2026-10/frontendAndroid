package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.repository

class UserRepositoryImpl(private val apiService: UserAPIService) : UserRepository {
    private val _saldo = MutableStateFlow(0)
    override val saldo: StateFlow<Int> = _saldo.asStateFlow()

    override fun actualizarSaldo(nuevoSaldo: Int) {
        _saldo.value = nuevoSaldo
    }

    override suspend fun fetchSaldoReal(): Int {
        val nuevoSaldo = apiService.getSaldo()
        _saldo.value = nuevoSaldo
        return nuevoSaldo
    }
}