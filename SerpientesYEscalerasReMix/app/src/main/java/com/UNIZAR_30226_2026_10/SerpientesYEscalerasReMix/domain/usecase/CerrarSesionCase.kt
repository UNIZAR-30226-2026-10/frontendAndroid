import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.repository.LoginRegisterRepository

class CerrarSesionCase(private val repository: LoginRegisterRepository) {
    suspend operator fun invoke() = repository.cerrarSesion()
}