package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.fakes

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Producto
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Tipo_Producto

val listaDePruebas = listOf(
    Producto("Escaleras nobles", 200,"La escalera de un ilustre señor", Tipo_Producto.valueOf("Escalera"), true),

    Producto("Escaleras reales", 500,"La escalera de un rey de bravo corazón", Tipo_Producto.valueOf("Escalera"), false),
    Producto("Escaleras de oro", 1000,"", Tipo_Producto.valueOf("Escalera"), false),
    Producto("Serpientes comunes", 100,"", Tipo_Producto.valueOf("Serpiente"), false),
    Producto("Serpientes venenosas", 400,"", Tipo_Producto.valueOf("Serpiente"), false),
    Producto("Serpientes gigantes", 800,"", Tipo_Producto.valueOf("Serpiente"), false),
    Producto("Icono Dia de la suerte", 500, "Un icono que te traerá suerte en el juego", Tipo_Producto.valueOf("Icono"), false),
    Producto("Icono Escudo", 800, "Un icono que te protegerá de las serpientes", Tipo_Producto.valueOf("Icono"), true),
    Producto("Icono Corona", 1200, "Un icono que te hará sentir como un rey", Tipo_Producto.valueOf("Icono"), false),
    Producto("Ficha episcopal", 300, "Una ficha con forma de mitra episcopal", Tipo_Producto.valueOf("Ficha"), false),
    Producto("Ficha de oro", 700, "Una ficha hecha de oro macizo", Tipo_Producto.valueOf("Ficha"), false),
    Producto("Ficha de diamante", 1500, "Una ficha incrustada con diamantes", Tipo_Producto.valueOf("Ficha"), false)
)