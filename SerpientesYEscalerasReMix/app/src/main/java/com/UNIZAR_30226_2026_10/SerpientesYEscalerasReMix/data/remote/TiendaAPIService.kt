package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.ComprarProductoRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.ProductoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TiendaAPIService {
    @GET("/store/{email}") //FIXME
    suspend fun getProductos(
        @Path("email") email: String
    ): List<ProductoDto>
     @POST("/store/{email}") //FIXME
     suspend fun comprarProducto(
         @Path("email") email: String,
         @Body nombreProducto: ComprarProductoRequest
    ): Response<Unit>

}