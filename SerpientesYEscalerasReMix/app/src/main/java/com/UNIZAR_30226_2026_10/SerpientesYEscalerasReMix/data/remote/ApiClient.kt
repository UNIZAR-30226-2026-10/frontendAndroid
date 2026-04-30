package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote

import android.content.Context
import android.util.Log
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.AuthResponse
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.LoginRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.RegisterRequest
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
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
    suspend fun cookieLogin(@Body body: Map<String, String> = emptyMap()): Response<AuthResponse>

    @POST("auth/new_users")
    suspend fun register(@Body body: RegisterRequest): Response<ResponseBody>
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

        // LOGING
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d("API_LOG", "RETROFIT: $message")
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        //COOKIE
        val forceInsecureInterceptor = Interceptor { chain ->
            val response = chain.proceed(chain.request())
            val cookieHeaders = response.headers("Set-Cookie")

            if (cookieHeaders.isNotEmpty()) {
                val modifiedResponse = response.newBuilder()
                modifiedResponse.removeHeader("Set-Cookie")
                for (header in cookieHeaders) {
                    // Quitamos 'secure' para que el BridgeInterceptor la acepte sobre HTTP
                    val insecureHeader = header.replace(Regex("(?i);\\s*secure"), "")
                    modifiedResponse.addHeader("Set-Cookie", insecureHeader)
                }
                modifiedResponse.build()
            } else {
                response
            }
        }

        // Cliente OKHttp
        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(forceInsecureInterceptor)
            .addNetworkInterceptor(loggingInterceptor)
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
        get() = _apiService
            ?: throw IllegalStateException("Debes llamar a ApiClient.init(context) primero")

    fun clearCookies() {
        _cookieJar?.clear()
    }
}
