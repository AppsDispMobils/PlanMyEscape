

package com.example.PlanMyEscape.ui.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.PlanMyEscape.R
import com.example.PlanMyEscape.ui.viewmodel.AuthState
import com.example.PlanMyEscape.ui.viewmodel.AuthenticationViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState


@Composable
fun RecoverPasswordScreen(
    navController: NavController,
    authViewModel: AuthenticationViewModel = hiltViewModel()
) {
    val email = remember { mutableStateOf("") }
    val error = remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    // Obtener el estado de la autenticación (cargando, éxito, error)
    val authState by authViewModel.authState.observeAsState()


    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Loading -> {
                error.value = null
            }
            is AuthState.Success -> {

                Toast.makeText(context, "Reenvío de correo exitoso.", Toast.LENGTH_LONG).show()

                navController.navigate("login")
            }
            is AuthState.Error -> {

                error.value = (authState as AuthState.Error).message
            }
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título de la pantalla
        Text(
            text = stringResource(R.string.recover_password),
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Campo de correo
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text(stringResource(R.string.enter_email)) },
            modifier = Modifier.fillMaxWidth()
        )

        // Mostrar mensaje de error si los correos no coinciden
        error.value?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón de enviar correo de recuperación
        Button(
            onClick = {
                if (email.value.isNotEmpty()) {
                    authViewModel.recoverPassword(email.value)
                } else {
                    error.value = "El correo no puede estar vacío"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.send_email))
        }
    }
}
