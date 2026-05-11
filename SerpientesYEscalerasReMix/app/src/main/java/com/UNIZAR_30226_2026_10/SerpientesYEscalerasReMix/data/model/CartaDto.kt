package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.R
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Calidad
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Carta
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Tipo_Carta
import com.google.gson.annotations.SerializedName

data class CartaDto(
    @SerializedName("nombre") val nombre: String,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("tipo") val tipo: String,
    @SerializedName("calidad") val calidad: String,
    // FIXME TEMP
    @SerializedName("id") val id: Int = 0,
    @SerializedName("efecto") val efecto: String = "Efecto $id"
)

// MAPPER
fun CartaDto.toDomain(): Carta {
    val imageResId = CARTAS_IMAGE_MAP[this.nombre] ?: R.drawable.carta_moises

    return Carta(
        nombre = this.nombre,
        descripcion = this.descripcion,
        tipo = try {
            Tipo_Carta.valueOf(this.tipo)
        } catch (e: Exception) {
            Tipo_Carta.Entorno // Valor por defecto en caso de que el tipo no sea reconocido
        },
        calidad = try {
            Calidad.valueOf(this.calidad)
        } catch (e: Exception) {
            Calidad.Comun // Valor por defecto en caso de que la calidad no sea reconocida
        },
        id = this.id,
        efecto = this.efecto,
        imagen = imageResId
    )
}

private val CARTAS_IMAGE_MAP = mapOf(
    "Moises" to R.drawable.carta_moises,
    "Wild Frank" to R.drawable.carta_wild_frank,
    "Carpintero" to R.drawable.carta_carpintero,
    "Dia de la marmota" to R.drawable.carta_dia_de_la_marmota,
    "Salto de longitud" to R.drawable.carta_salto_de_longitud,
    "Robo de identidad" to R.drawable.carta_robo_de_identidad,
    "Mal de ojo" to R.drawable.carta_mal_de_ojo,
    "Antidoto" to R.drawable.carta_antidoto,
    "Pickpocket" to R.drawable.carta_pickpocket,
    "Dado envenenado" to R.drawable.carta_dado_envenenado,
    "Dado dorado" to R.drawable.carta_dado_dorado,
    "Serpiente en tu bota" to R.drawable.cata_serpiente_en_tu_bota,
    "Parca" to R.drawable.carta_parca,
    "Cambiar de idea" to R.drawable.carta_cambiar_de_idea,
    "Agujero de serpiente" to R.drawable.carta_agujero_de_serpiente,
    "Bolsillo roto" to R.drawable.carta_bolsillo_roto,
    "Compañerismo obligado" to R.drawable.carta_companerismo_obligatorio,
    "Coleccionista" to R.drawable.carta_coleccionista,
    "Noqueo" to R.drawable.carta_noqueo,
    "Exceso de medios" to R.drawable.carta_exceso_de_medios
)