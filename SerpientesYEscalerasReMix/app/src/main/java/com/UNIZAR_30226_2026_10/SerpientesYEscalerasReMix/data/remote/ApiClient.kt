package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote

import android.content.Context
import android.util.Log
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.AceptarInvitacionRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.AnadirBotRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.AuthReply
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.CrearLobbyRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.GetAmigosReply
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.GetInvitacionesReply
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.GetPartidasReply
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.IniciarPartidaRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.LeaveOrExpelRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.LobbyReply
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.LoginRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.PartidaReply
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.PostInvitacionRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.RegisterRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.SeleccionMazoRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.SetBoardRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.SetReadyRequest
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

    @GET("lobbies/by-player/{username}")
    suspend fun getLobbyByPlayer(@Path("username") username: String): Response<LobbyReply>

    @GET("lobbies/{lobbyId}")
    suspend fun getLobby(@Path("lobbyId") lobbyId: String): Response<LobbyReply>

    @POST("lobbies/{lobbyId}/bots")
    suspend fun addBot(@Path("lobbyId") lobbyId: String, @Body request: AnadirBotRequest): Response<ResponseBody>

    @GET("boards")
    suspend fun getAllBoards(): Response<List<String>>

    @PUT("lobbies/{lobbyId}/board")
    suspend fun setBoard(@Path("lobbyId") lobbyId: String, @Body body: SetBoardRequest): Response<ResponseBody>

    @PUT("lobbies/{lobbyId}/players/{username}/deck")
    suspend fun selectDeck(@Path("lobbyId") lobbyId: String, @Path("username") username: String, @Body request: SeleccionMazoRequest): Response<ResponseBody>

    @PUT("lobbies/{lobbyId}/players/{username}/ready")
    suspend fun setReady(@Path("lobbyId") lobbyId: String, @Path("username") username: String, @Body body: SetReadyRequest): Response<ResponseBody>

    @HTTP(method = "DELETE", path = "lobbies/{lobbyId}/players/{username}", hasBody = true)
    suspend fun leaveOrExpel(@Path("lobbyId") lobbyId: String, @Path("username") username: String, @Body body: LeaveOrExpelRequest): Response<ResponseBody>

    // FUNCIONES JUGAR-AMIGOS

    @POST("lobbies/{lobbyId}/invitations")
    suspend fun sendInvitation(@Path("lobbyId") lobbyId: String, @Body request: PostInvitacionRequest): Response<ResponseBody>

    @PUT("lobbies/{lobbyId}/invitations")
    suspend fun respondInvitation(@Path("lobbyId") lobbyId: String, @Body request: AceptarInvitacionRequest): Response<LobbyReply>

    @GET("users/{username}/invites")
    suspend fun getInvitations(@Path("username") username: String): Response<GetInvitacionesReply>

    @POST("users/{email}/{friendUsername}/invites")
    suspend fun addFriend(@Path("email") email: String, @Path("friendUsername") friendUsername: String): Response<ResponseBody>

    @HTTP(method = "DELETE", path = "users/{email}/friends/{friendUsername}", hasBody = true)
    suspend fun removeFriend(@Path("email") email: String, @Path("friendUsername") friendUsername: String, @Body body: Map<String, String>): Response<ResponseBody>

    @GET("users/{email}/friends")
    suspend fun getFriends(@Path("email") email: String): Response<GetAmigosReply>

    // FUNCIONES JUGAR-CONTINUAR

    @GET("users/{email}/matches")
    suspend fun getMatches(@Path("email", encoded = true) email: String): Response<GetPartidasReply>

    // FUNCIONES PARTIDA
    @POST("matches")
    suspend fun startMatch(@Body body: IniciarPartidaRequest): Response<PartidaReply>

}

object ApiClient {
    // private const val API_URL = "http://syeremix.switzerlandnorth.cloudapp.azure.com/api/"
    private const val API_URL = "http://192.168.1.36:3000/api/"

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
                    val insecureHeader = header
                        .replace(Regex("(?i);\\s*secure"), "")
                        .replace(Regex("(?i);\\s*SameSite=[a-z]+"), "")

                    modifiedResponse.addHeader("Set-Cookie", insecureHeader)
                }
                modifiedResponse.build()
            } else {
                response
            }
        }

        // Cliente OKHttp
        val okHttpClient = OkHttpClient.Builder()
            .protocols(listOf(okhttp3.Protocol.HTTP_1_1)) // TODO ELIMINAR ESTA LINEA CUANDO NO SE TRABAJE EN LOCAL 192.168.1.36
            .addInterceptor(forceInsecureInterceptor)
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
