package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.LogrosRepository
import kotlinx.coroutines.flow.StateFlow

class ObtenerLogrosCase(
    private val email: StateFlow<String>,
    private val repository: LogrosRepository
) {
    suspend operator fun invoke(): List<LogroUsuario> {
        val globalAchievements = repository.getAllAchievements()
        val statsResponse      = repository.getUserStats(email.value)
        val reclamados         = statsResponse.logrosCompletados.toSet()

        return globalAchievements.map { dto ->
            val progreso = when (dto.tipoRecompensa) {
                "Victorias"           -> statsResponse.victorias
                "Partidas"            -> statsResponse.partidasJugadas
                "Derrotas"            -> statsResponse.derrotas
                "SEP"                 -> statsResponse.sep
                "CartasJugadas"       -> statsResponse.cartasJugadas
                "CartasLegendarias"   -> statsResponse.cartasLegendarias
                "NumeroAmigos"        -> statsResponse.numeroAmigos
                "LogrosDesbloqueados" -> statsResponse.logrosCompletados.size
                else                  -> 0
            }
            LogroUsuario(
                id                  = dto.id,
                nombre              = dto.nombre,
                descripcion         = dto.descripcion,
                progresoActual      = progreso,
                progresoObjetivo    = dto.objetivo,
                tipoRecompensa      = dto.tipoRecompensa,
                valorRecompensa     = dto.valorRecompensa?.toString() ?: "",
                imagen              = imagenParaCarta(dto.cartaID),
                esCompletado        = progreso >= dto.objetivo,
                recompensaReclamada = dto.id in reclamados
            )
        }
    }

    private fun imagenParaCarta(cartaID: String?): Int {
        return when (cartaID) {
            "Serpiente en tu bota" -> R.drawable.cata_serpiente_en_tu_bota
            "Wild Frank"           -> R.drawable.carta_wild_frank
            "Moises"               -> R.drawable.carta_moises
            "Noqueo"               -> R.drawable.carta_noqueo
            "Parca"                -> R.drawable.carta_parca
            "Agujero de serpiente" -> R.drawable.carta_agujero_de_serpiente
            "Antidoto"             -> R.drawable.carta_antidoto
            "Cambiar de idea"      -> R.drawable.carta_cambiar_de_idea
            "Dia de la marmota"    -> R.drawable.carta_dia_de_la_marmota
            "Coleccionista"        -> R.drawable.carta_coleccionista
            "Companerismo obligatorio" -> R.drawable.carta_companerismo_obligatorio
            "Bolsillo roto"        -> R.drawable.carta_bolsillo_roto
            "Pickpocket"           -> R.drawable.carta_pickpocket
            "Carpintero"           -> R.drawable.carta_carpintero
            "Mal de ojo"           -> R.drawable.carta_mal_de_ojo
            "Robo de identidad"    -> R.drawable.carta_robo_de_identidad
            "Salto de longitud"    -> R.drawable.carta_salto_de_longitud
            "Exceso de medios"     -> R.drawable.carta_exceso_de_medios
            "Dado dorado"          -> R.drawable.carta_dado_dorado
            "Dado envenenado"      -> R.drawable.carta_dado_envenenado
            else                   -> 0
        }
    }
}