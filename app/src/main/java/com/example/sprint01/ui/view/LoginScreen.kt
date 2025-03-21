package com.example.sprint01.ui.view

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
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
import androidx.navigation.NavController
import com.example.sprint01.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showAlert by remember { mutableStateOf(false) }

    var showError by remember { mutableStateOf(false) }
    var usernameError by remember {mutableStateOf(false)}
    var passwordError by remember {mutableStateOf(false)}

    val defaultUser = stringResource(id = R.string.username)
    val defaultPass = stringResource(id = R.string.password)

    val appColor = colorResource(id = R.color.app_color)

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
            value = username,
            onValueChange = {
                username = it
                Log.d("LoginScreen", "Username entered: $username") // Log para el nombre de usuario
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
                Log.d("LoginScreen", "Login button clicked") // Log para el clic en el botón de login
                if (username == defaultUser && password == defaultPass) {
                    Log.d("LoginScreen", "Login successful for user: $username")
                    navController.navigate("home")
                } else {
                    showError = true
                    usernameError = username != defaultUser
                    passwordError = password != defaultPass
                    Log.d("LoginScreen", "Login failed: Incorrect username or password")
                }
            },
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
