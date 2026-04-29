package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.components

import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.usecase.CaseFacade
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.Destinos
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.navigation.SENavHostController
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.prepararOrientacion
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.SETextTypes
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_negative
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.ui.theme.color_text
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun LogoutBoton(scope: CoroutineScope, SEState: SENavHostController, cF: CaseFacade) {

    Surface(
        color = color_negative,
        shape = RoundedCornerShape(16.dp),
        onClick = { logoutBotonAccion(scope, SEState, cF) }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(4.dp))
        {

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ExitToApp, // O uno similar
                contentDescription = null,
                tint = color_text,
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterVertically) // Se alinea a la derecha del Box
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text("Amigos", style = SETextTypes.plano)

            Spacer(modifier = Modifier.width(8.dp))
        }

    }
}

fun logoutBotonAccion(scope: CoroutineScope, SEState: SENavHostController, cF: CaseFacade) {
    scope.launch {
        // cF.loginRegisterCase.cerrarSesion() TODO CHANGE

        // Preparar el cambio a vertical para el login
        prepararOrientacion(SEState, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        SEState.goTo(Destinos.LOGIN)
    }
}
