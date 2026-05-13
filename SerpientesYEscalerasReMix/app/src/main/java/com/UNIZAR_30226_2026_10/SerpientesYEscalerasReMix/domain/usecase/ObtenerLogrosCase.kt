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
        val statsResponse = repository.getUserStats(email.value)
        val reclamados = statsResponse.logrosCompletados.toSet()

        return globalAchievements.map { dto ->
            val progreso = when (dto.tipoRecompensa) {
                "Victorias" -> statsResponse.victorias
                "Partidas" -> statsResponse.partidasJugadas
                "Derrotas" -> statsResponse.derrotas
                "SEP" -> statsResponse.sep
                "CartasJugadas" -> statsResponse.cartasJugadas
                "CartasLegendarias" -> statsResponse.cartasLegendarias
                "NumeroAmigos" -> statsResponse.numeroAmigos
                "LogrosDesbloqueados" -> statsResponse.logrosCompletados.size
                else -> 0
            }

            // Mapeo de los logros porque recibimos cartaID=null incluso cuando es una carta la recompensa
            val cartaIDReal = when (dto.id) {
                "Primeros pasos" -> "Serpiente en tu bota"
                "Imparable" -> "Wild Frank"
                "Derrotado" -> "Pickpocket"
                "Negado" -> "Carpintero"
                "Completista" -> "Mal de ojo"
                "Platino" -> "icono_jugador_platino"
                "En racha" -> "icono_jugador_w"
                "Resiliente" -> "icono_jugador_l"
                "Coleccionista" -> "icono_jugador_completista"
                "Manos a la obra" -> "escalera_estratega"
                "Estratega" -> "escalera_magnate"
                else -> null
            }

            val esEscalera = dto.tipoRecompensa == "Escalera" || dto.id.contains("Escalera", ignoreCase = true)
            val esIcono = dto.tipoRecompensa == "Icono" || dto.id.contains("Avatar", ignoreCase = true)

            // Lógica para el TEXTO de la recompensa
            val textoRecompensa = when {
                dto.tipoRecompensa == "SEP" -> {
                    val cantidad = dto.valorRecompensa ?: dto.objetivo
                    "$cantidad SEP"
                }
                cartaIDReal == "icono_jugador_platino" || cartaIDReal == "icono_jugador_w"
                        || cartaIDReal == "icono_jugador_l" || cartaIDReal == "icono_jugador_completista" -> "Icono"
                cartaIDReal == "escalera_magnate" || cartaIDReal == "escalera_estratega" -> "Skin"
                cartaIDReal != null && cartaIDReal.isNotBlank() -> "Carta"
                // Para todo lo demás (Skins, Fichas o nulos), enviamos vacío
                else -> ""
            }

            // 3. DETERMINACIÓN DEL RECURSO DE IMAGEN
            val recursoImagen = when {
                cartaIDReal == "icono_jugador_platino" -> R.drawable.icono_jugador_platino
                cartaIDReal == "icono_jugador_w"       -> R.drawable.icono_jugador_w
                cartaIDReal == "icono_jugador_l"       -> R.drawable.icono_jugador_l
                cartaIDReal == "icono_jugador_completista" -> R.drawable.icono_jugador_completista
                cartaIDReal == "escalera_estratega" -> R.drawable.escalera_estratega
                cartaIDReal == "escalera_magnate"       -> R.drawable.escalera_magnate

                // Si es Carta: Usamos el mapeo de imágenes
                cartaIDReal != null && cartaIDReal.isNotBlank() -> imagenParaCarta(cartaIDReal)

                // Si no es Carta (incluyendo SEP y Monedas): No ponemos imagen
                else -> 0
            }

            LogroUsuario(
                id = dto.id,
                nombre = dto.nombre,
                descripcion = dto.descripcion,
                progresoActual = progreso,
                progresoObjetivo = dto.objetivo,
                tipoRecompensa = dto.tipoRecompensa,
                valorRecompensa = textoRecompensa,
                imagen = recursoImagen,
                esCompletado = progreso >= dto.objetivo,
                recompensaReclamada = dto.id in reclamados
            )
        }
    }

    private fun imagenParaCarta(logroID: String?): Int {
        return when (logroID) {
            "Serpiente en tu bota" -> R.drawable.cata_serpiente_en_tu_bota
            "Wild Frank" -> R.drawable.carta_wild_frank
            "Moises" -> R.drawable.carta_moises
            "Noqueo" -> R.drawable.carta_noqueo
            "Parca" -> R.drawable.carta_parca
            "Agujero de serpiente" -> R.drawable.carta_agujero_de_serpiente
            "Antidoto" -> R.drawable.carta_antidoto
            "Cambiar de idea" -> R.drawable.carta_cambiar_de_idea
            "Dia de la marmota" -> R.drawable.carta_dia_de_la_marmota
            "Coleccionista" -> R.drawable.carta_coleccionista
            "Companerismo obligatorio" -> R.drawable.carta_companerismo_obligatorio
            "Bolsillo roto" -> R.drawable.carta_bolsillo_roto
            "Pickpocket" -> R.drawable.carta_pickpocket
            "Carpintero" -> R.drawable.carta_carpintero
            "Mal de ojo" -> R.drawable.carta_mal_de_ojo
            "Robo de identidad" -> R.drawable.carta_robo_de_identidad
            "Salto de longitud" -> R.drawable.carta_salto_de_longitud
            "Exceso de medios" -> R.drawable.carta_exceso_de_medios
            "Dado dorado" -> R.drawable.carta_dado_dorado
            "Dado envenenado" -> R.drawable.carta_dado_envenenado
            else -> R.drawable.corona
        }
    }

    // Nueva función para manejar las imágenes de las escaleras
    private fun imagenParaEscalera(logroID: String): Int {
        return when {
            logroID.contains("Estratega", ignoreCase = true) -> R.drawable.escalera_estratega
            logroID.contains("Jungla", ignoreCase = true) -> R.drawable.escalera_jungla
            logroID.contains("Magnate", ignoreCase = true) -> R.drawable.escalera_magnate
            logroID.contains("Basico", ignoreCase = true) -> R.drawable.escalera
            else -> R.drawable.escalera
        }
    }
}