package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model

enum class Tipo_Producto {
    Icono,
    Ficha,
    Serpiente,
    Escalera,
    Desconocido
}

data class Producto(
    val nombre: String,
    val precio: Int,
    val descripcion: String,
    val tipo: Tipo_Producto,
    val enPosesion: Boolean,
    // Este valor es para cargar la imagen desde recursos locales. Si es null, se puede usar una imagen por defecto
    // necesaria ya que para cargar con Image obtener el nombre al momento de la carga no es muy recomendable, lo malo de
    // esto es que necesitamos conocer el nombre de los productos de antemano
    val imageResId: Int? = null
)
