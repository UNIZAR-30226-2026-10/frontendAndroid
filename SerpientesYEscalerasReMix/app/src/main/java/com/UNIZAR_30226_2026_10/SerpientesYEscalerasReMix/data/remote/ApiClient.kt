package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote

import android.content.Context
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.AuthResponse
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.LoginRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.RegisterRequest
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    // FUNCIONES PARA PRUEBAS Y LOGGING EN MAIN_ACTIVITY
    @GET("achievements/ping")
    suspend fun pingAchievements(): Response<Unit>

    @GET("auth/ping")
    suspend fun pingAuth(): Response<Unit>

    @GET("cards/ping")
    suspend fun pingCards(): Response<Unit>

    @GET("lobbies/ping")
    suspend fun pingLobbies(): Response<Unit>

    @GET("matches/ping")
    suspend fun pingMatches(): Response<Unit>

    @GET("users/ping")
    suspend fun pingUsers(): Response<Unit>

    // FUNCIONES AUTH

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("auth/cookie_login")
    suspend fun cookieLogin(): Response<AuthResponse>

    @POST("auth/new_users")
    suspend fun register(@Body body: RegisterRequest): Response<String>
}

object ApiClient {
    private const val API_URL = "http://syeremix.switzerlandnorth.cloudapp.azure.com/api/"

    private var _apiService: ApiService? = null
    private var _cookieJar: PersistentCookieJar? = null

    // Inicializa el cliente -> MainActivity onCreate
    fun init(context: Context) {
        if (_apiService != null) return // Evitar re-inicializar

        // Cookies Persistentes
        val cookieJar = PersistentCookieJar(
            SetCookieCache(),
            SharedPrefsCookiePersistor(context)
        )
        _cookieJar = cookieJar

        // Cliente OKHttp
        val okHttpClient = OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .build()

        // Retrofit
        _apiService = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    // Utilizable desde el resto de paquetes
    val apiService: ApiService
        get() = _apiService ?: throw IllegalStateException("Debes llamar a ApiClient.init(context) primero")

    fun clearCookies() {
        _cookieJar?.clear()
    }
}
