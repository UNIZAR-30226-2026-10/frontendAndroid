package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.CartaDto
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.MazoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.DELETE

//FIXME revisar rutas de la api y requisitos
interface MazosAPIService {
    @GET("/{email}/decks")
    suspend fun getMazos(
        @Path("email") email: String
    ) : List<MazoDto>

    @GET("/{email}/decks/{id}/cards")
    suspend fun getCartasMazo(
        @Path("email") email: String,
        @Path("id") id: String
    ) : List<CartaDto>

    @POST("/{email}/decks")
    suspend fun crearMazo(
        @Path("email") email: String,
        @Body nuevoMazo: MazoDto
    ) : Response<Unit>

    @DELETE("/{email}/decks/{id}")
    suspend fun eliminarMazo(
        @Path("email") email: String,
        @Path("id") id: String
    ) : Response<Unit>

    @POST("/{email}/decks/{id}")
    suspend fun editarMazo(
        @Path("email") email: String,
        @Path("id") id: String,
        @Body nuevoNombre: String?,
        @Body nuevasCartas: List<CartaDto>?,
        @Body eliminarCartas: List<CartaDto>?
    ) : Response<Unit>

    @GET("/{email}/cards")
    suspend fun getCartasDisponibles(
        @Path("email") email: String
    ) : List<CartaDto>
}