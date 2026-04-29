import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.LoginRegisterRepository

class RegistrarseCase(private val repository: LoginRegisterRepository) {
    suspend operator fun invoke(username: String, email: String, passwd: String): Boolean =
        repository.registrarse(username, email, passwd)
}