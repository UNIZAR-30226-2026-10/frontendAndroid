package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository
//FIXME rutas despues del merge, revisar si hay que cambiar algo
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Carta
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.Mazo

interface MazosRepository {
    // Devuelve la lista de mazos del usuario
    suspend fun getMazos(email: String) : List<Mazo>
    // Devuelve las cartas del mazo con el id dado, si no existe devuelve una lista vacia
    suspend fun getCartasMazo(email: String, id: String) : List<Carta>
    // FIXME NO CLARO SI LO LLAMA ASI LA API Crea un nuevo mazo con el nombre dado y lo devuelve, con una lista de cartas vacia
    suspend fun crearMazo(email: String, nuevoMazo: Mazo): Boolean
    // Elimina el mazo con el id dado, si no existe no hace nada
    suspend fun eliminarMazo(email: String, id: String) : Boolean
    // Edita el mazo con el id dado, cambiando su nombre y/o su lista de cartas, si no existe no hace nada
    suspend fun editarMazo(email: String, id: String, nuevoNombre: String?, nuevasCartas: List<Carta>?, eliminarCartas: List<Carta>?): Boolean
    // Devuelve la lista de cartas disponibles para añadir a los mazos
    suspend fun getCartasDisponibles(email: String) : List<Carta>
}