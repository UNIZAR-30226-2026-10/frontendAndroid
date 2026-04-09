package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.TiendaRepository

class GetSaldoCase(private val tiendaRepository: TiendaRepository) {
    suspend operator fun invoke(): Int {
        //TODO añadir reglas de negocio antes de devolver el saldo
        return tiendaRepository.getSaldo()
    }
}