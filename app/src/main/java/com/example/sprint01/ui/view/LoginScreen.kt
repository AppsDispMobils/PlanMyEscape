package com.example.sprint01.ui.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sprint01.R
import com.example.sprint01.ui.viewmodel.AuthenticationViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import com.example.sprint01.ui.viewmodel.AuthState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel : AuthenticationViewModel = hiltViewModel()
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showAlert by remember { mutableStateOf(false) }

    var showError by remember { mutableStateOf(false) }
    var usernameError by remember {mutableStateOf(false)}
    var passwordError by remember {mutableStateOf(false)}

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    val defaultUser = stringResource(id = R.string.username)
    val defaultPass = stringResource(id = R.string.password)

    val appColor = colorResource(id = R.color.app_color)

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated -> navController.navigate("home")
            is AuthState.Error -> Toast.makeText(context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título
        Text(
            text = stringResource(id = R.string.Login_Screen),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Campo de nombre de usuario
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                Log.d("LoginScreen", "Username entered: $email") // Log para el nombre de usuario
            },
            label = { Text(stringResource(id=R.string.UsernameText), color = Color.DarkGray) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Email",
                    tint = appColor
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = appColor,
                unfocusedBorderColor = if (usernameError) Color.Red else Color.Gray,
                cursorColor = appColor,
                focusedTextColor = Color.DarkGray
            ),
            isError = usernameError
        )
        if (usernameError) {
            Text(
                text = stringResource(id=R.string.UsernameText1),
                color = Color.Red,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de contraseña
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                Log.d("LoginScreen", "Password entered: $password") // Log para la contraseña
            },
            label = { Text(stringResource(id = R.string.PasswordText), color = Color.DarkGray) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Lock",
                    tint = appColor
                )
            },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = appColor,
                unfocusedBorderColor = if (passwordError) Color.Red else Color.Gray,
                cursorColor = appColor,
                focusedTextColor = Color.DarkGray
            ),
            isError = passwordError
        )
        if (passwordError) {
            Text(
                text = stringResource(id = R.string.PasswordText1),
                color = Color.Red,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Botón de inicio de sesión
        Button(
            onClick = {
                authViewModel.login(email, password)
            },
            enabled = authState.value != AuthState.Loading,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = appColor,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp) // Bordes redondeados
        ) {
            Text(text = stringResource(id = R.string.Login_Text), fontSize = 16.sp, color = Color.Gray)
        }
        TextButton(onClick = {
            navController.navigate("signup")
        }) {
            Text("Registrarse")
        }

        // Mostrar alerta si hay un error
        if (showError) {
            AlertDialog(
                onDismissRequest = { showError = false },
                confirmButton = {
                    TextButton(onClick = { showError = false }) {
                        Text("OK")
                    }
                },
                title = { Text(stringResource(id = R.string.Login_Error)) },
                text = { Text(stringResource(id = R.string.Login_Error1)) }
            )
        }
    }
}
