package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Calidad
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Carta
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Tipo
import com.google.gson.annotations.SerializedName

data class CartaDto(
    @SerializedName("nombre") val nombre: String,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("tipo") val tipo: String,
    @SerializedName("calidad") val calidad: String,
    // FIXME TEMP
    @SerializedName("id") val id: Int = 0,
    @SerializedName("efecto") val efecto: String = "Efecto $id",
    @SerializedName("imagen") val imagen: Int = 0
)

// MAPPER
fun CartaDto.toDomain(): Carta {
    return Carta(
        nombre = this.nombre,
        descripcion = this.descripcion,
        tipo = try {
            Tipo.valueOf(this.tipo)
        } catch (e: Exception) {
            Tipo.Entorno // Valor por defecto en caso de que el tipo no sea reconocido
        },
        calidad = try {
            Calidad.valueOf(this.calidad)
        } catch (e: Exception) {
            Calidad.Comun // Valor por defecto en caso de que la calidad no sea reconocida
        },
        id = this.id,
        efecto = this.efecto,
        imagen = this.imagen
    )
}