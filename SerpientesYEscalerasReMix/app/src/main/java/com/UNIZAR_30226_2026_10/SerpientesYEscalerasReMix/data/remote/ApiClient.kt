package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote

import android.content.Context
import android.util.Log
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.AceptarInvitacionRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.AnadirBotRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.AuthReply
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.CrearLobbyRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.InvitacionRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.LobbyReply
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.LoginRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.RegisterRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.SeleccionMazoRequest
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
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

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
    suspend fun login(@Body request: LoginRequest): Response<AuthReply>

    @POST("auth/cookie_login")
    suspend fun cookieLogin(@Body body: Map<String, String> = emptyMap()): Response<AuthReply>

    @POST("auth/new_users")
    suspend fun register(@Body body: RegisterRequest): Response<ResponseBody>

    // FUNCIONES JUGAR_CREAR

    @POST("lobbies")
    suspend fun createLobby(@Body request: CrearLobbyRequest): Response<LobbyReply>

    @GET("lobbies/{lobbyId}")
    suspend fun getLobby(@Path("lobbyId") lobbyId: String): Response<LobbyReply>

    @POST("lobbies/{lobbyId}/bots")
    suspend fun addBot(@Path("lobbyId") lobbyId: String, @Body request: AnadirBotRequest): Response<ResponseBody>

    @POST("lobbies/{lobbyId}/invitations")
    suspend fun sendInvitation(@Path("lobbyId") lobbyId: String, @Body request: InvitacionRequest): Response<ResponseBody>

    @PUT("lobbies/{lobbyId}/invitations")
    suspend fun respondInvitation(@Path("lobbyId") lobbyId: String, @Body request: AceptarInvitacionRequest): Response<ResponseBody>

    @PUT("lobbies/{lobbyId}/board")
    suspend fun setBoard(@Path("lobbyId") lobbyId: String, @Body body: Map<String, String>): Response<ResponseBody>

    @PUT("lobbies/{lobbyId}/players/{email}/deck")
    suspend fun selectDeck(@Path("lobbyId") lobbyId: String, @Path("email") email: String, @Body request: SeleccionMazoRequest): Response<ResponseBody>

    @PUT("lobbies/{lobbyId}/players/{email}/ready")
    suspend fun setReady(@Path("lobbyId") lobbyId: String, @Path("email") email: String, @Body body: Map<String, Boolean>): Response<ResponseBody>

    @HTTP(method = "DELETE", path = "lobbies/{lobbyId}/players/{email}", hasBody = true)
    suspend fun leaveOrExpel(@Path("lobbyId") lobbyId: String, @Path("email") email: String, @Body body: Map<String, String>): Response<ResponseBody>
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
