package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote

import android.content.Context
import android.util.Log
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.CartaDto
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.CartasDisponiblesResponseDto
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.ComprarProductoRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.MazoDto
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.MazosResponseDto
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.ProductoDto
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.model.SaldoDto
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.AceptarInvitacionRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.ActualizarEscaleraRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.ActualizarFichaRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.ActualizarIconoRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.ActualizarSerpienteRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.AnadirBotRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.AuthReply
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.ChatRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.CrearLobbyRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.EscalerasReply
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.FichasReply
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.GetAmigosReply
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.GetChatReply
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.GetInvitacionesReply
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.GetPartidasReply
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.IconosReply
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.IniciarPartidaRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.JugarCartaRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.LeaveOrExpelRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.LobbyReply
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.LoginRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.PerfilUsuarioReply
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.PartidaReply
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.PostInvitacionRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.RegisterRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.RollDiceReply
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.SeleccionMazoRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.SerpientesReply
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.SetBoardRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.SetReadyRequest
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.remote.message_model.UpdatePawnRequest
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
import retrofit2.http.DELETE
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

    @POST("matches/{matchId}/chat/{username}")
    suspend fun sendChatMessage(@Path("matchId") matchId: String, @Path("username") username: String, @Body request: ChatRequest): Response<GetChatReply>

    @GET("matches/{matchId}/chat/{username}")
    suspend fun getChat(@Path("matchId") matchId: String, @Path("username") username: String): Response<GetChatReply>

    @GET("matches/{matchId}/{username}")
    suspend fun getMatchStatus(@Path("matchId") matchId: String, @Path("username") username: String): Response<PartidaReply>

    @POST("matches/{matchId}/cards/{username}")
    suspend fun playCard(@Path("matchId") matchId: String, @Path("username") username: String, @Body request: JugarCartaRequest): Response<PartidaReply>

    @POST("matches/{matchId}/dice/{username}")
    suspend fun rollDice(@Path("matchId") matchId: String, @Path("username") username: String): Response<RollDiceReply>

    @POST("matches/{matchId}/pawn/{username}")
    suspend fun updatePawn(@Path("matchId") matchId: String, @Path("username") username: String, @Body request: UpdatePawnRequest): Response<PartidaReply>

    // FUNCIONES TIENDA FIXME MIRAR LO DE LAS REPLYS
    @GET("cosmetics/store/{email}") //FIXME
    suspend fun getProductos(
        @Path("email") email: String
    ): Response<List<ProductoDto>>

    @POST("cosmetics/store/{email}") //FIXME
    suspend fun comprarProducto(
        @Path("email") email: String,
        @Body cosmetic_name: ComprarProductoRequest
    ): Response<Unit>

    @GET("users/{email}/SEP")
    suspend fun getSaldo(
        @Path("email") email: String
    ): Response<SaldoDto>

    // FUNCIONES MAZOS
    @GET("users/{email}/decks")
    suspend fun getMazos(
        @Path("email") email: String
    ) : Response<MazosResponseDto>

    @GET("users/{email}/decks/{id}/cards")
    suspend fun getCartasMazo(
        @Path("email") email: String,
        @Path("id") id: String
    ) : Response<List<CartaDto>>

    @POST("users/{email}/decks")
    suspend fun crearMazo(
        @Path("email") email: String,
        @Body nuevoMazo: MazoDto
    ) : Response<Unit>

    @DELETE("users/{email}/decks/{id}")
    suspend fun eliminarMazo(
        @Path("email") email: String,
        @Path("id") id: String
    ) : Response<Unit>

    @POST("users/{email}/decks/{id}")
    suspend fun editarMazo(
        @Path("email") email: String,
        @Path("id") id: String,
        @Body nuevoNombre: String?,
        @Body nuevasCartas: List<CartaDto>?,
        @Body eliminarCartas: List<CartaDto>?
    ) : Response<Unit>

    @GET("users/{email}/cards")
    suspend fun getCartasDisponibles(
        @Path("email") email: String
    ) : Response<CartasDisponiblesResponseDto>

    // FUNCIONES PERFIL

    @GET("users/{email}/profile")
    suspend fun getUserProfile(@Path("email") email: String): Response<PerfilUsuarioReply>

    @PUT("users/{email}/username")
    suspend fun updateUsername(@Path("email") email: String, @Body body: Map<String, String>): Response<ResponseBody>

    // Actualizar cosméticos — endpoint separado por tipo según la API
    @PUT("users/{email}/icon")
    suspend fun updateIcon(@Path("email") email: String, @Body body: ActualizarIconoRequest): Response<ResponseBody>

    @PUT("users/{email}/pawn")
    suspend fun updatePawn(@Path("email") email: String, @Body body: ActualizarFichaRequest): Response<ResponseBody>

    @PUT("users/{email}/snake")
    suspend fun updateSnake(@Path("email") email: String, @Body body: ActualizarSerpienteRequest): Response<ResponseBody>

    @PUT("users/{email}/stair")
    suspend fun updateStair(@Path("email") email: String, @Body body: ActualizarEscaleraRequest): Response<ResponseBody>

    // Obtener cosméticos disponibles del usuario — endpoint separado por tipo según la API
    @GET("users/{email}/icons")
    suspend fun getUserIcons(@Path("email") email: String): Response<IconosReply>

    @GET("users/{email}/pawns")
    suspend fun getUserPawns(@Path("email") email: String): Response<FichasReply>

    @GET("users/{email}/snakes")
    suspend fun getUserSnakes(@Path("email") email: String): Response<SerpientesReply>

    @GET("users/{email}/stairs")
    suspend fun getUserStairs(@Path("email") email: String): Response<EscalerasReply>

}

object ApiClient {
    private const val API_URL = "http://syeremix.switzerlandnorth.cloudapp.azure.com/api/"
    //private const val API_URL = "http://192.168.1.36:3000/api/"

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
            // .protocols(listOf(okhttp3.Protocol.HTTP_1_1)) // TODO ELIMINAR ESTA LINEA CUANDO NO SE TRABAJE EN LOCAL 192.168.1.36
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
