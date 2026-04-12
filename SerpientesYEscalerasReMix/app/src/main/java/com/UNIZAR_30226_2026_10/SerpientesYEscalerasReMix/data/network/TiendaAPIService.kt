package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.network

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.ProductoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TiendaAPIService {
    @GET("ruta") //FIXME
    suspend fun getProductos(): List<ProductoDto>

     @POST("ruta") //FIXME lo del path no se yo
     suspend fun comprarProducto(@Path("nombreProducto") nombreProducto: String): Response<Unit>

     @GET("ruta") //FIXME
     suspend fun getSaldo(): Int
}