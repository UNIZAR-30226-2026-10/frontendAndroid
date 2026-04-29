import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.LoginRegisterRepository

class IniciarSesionCase(private val repository: LoginRegisterRepository) {
    suspend operator fun invoke(email: String, passwd: String): Boolean =
        repository.iniciarSesion(email, passwd)
}