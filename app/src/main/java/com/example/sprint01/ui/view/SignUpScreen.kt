package com.example.sprint01.ui.view

import android.icu.util.Calendar
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import android.app.DatePickerDialog
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.TextButton
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sprint01.R
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sprint01.domain.model.User
import com.example.sprint01.ui.viewmodel.AuthState
import com.example.sprint01.ui.viewmodel.AuthenticationViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    navController: NavController,
    authViewModel : AuthenticationViewModel = hiltViewModel()
) {
    var username by remember { mutableStateOf("")}
    var email by remember { mutableStateOf("")}
    var password by remember { mutableStateOf("")}
    var birthdate by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var receiveEmails by remember { mutableStateOf(false) }

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    LaunchedEffect(authState.value) {
        when(authState.value) {
            is AuthState.Authenticated -> navController.navigate("home")
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT
            ).show()
            else -> Unit
        }
    }
    val appColor = colorResource(id = R.color.app_color)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Registrarse",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = username,
            onValueChange = {
               username = it
            },
            label = { Text(text = "Username", color = Color.DarkGray) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Username",
                    tint = appColor
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = appColor,
                unfocusedBorderColor = Color.Gray,
                cursorColor = appColor,
                focusedTextColor = Color.DarkGray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = { Text(text = "Email", color = Color.DarkGray) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email",
                    tint = appColor
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = appColor,
                unfocusedBorderColor = Color.Gray,
                cursorColor = appColor,
                focusedTextColor = Color.DarkGray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = { Text(text = "password", color = Color.DarkGray) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password",
                    tint = appColor
                )
            },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = appColor,
                unfocusedBorderColor = Color.Gray,
                cursorColor = appColor,
                focusedTextColor = Color.DarkGray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    val datePicker = DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            birthdate = "%04d-%02d-%02d".format(year, month + 1, dayOfMonth)
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    )

                    val minDate = Calendar.getInstance().apply {
                        set(1960, Calendar.JANUARY, 1)
                    }

                    datePicker.datePicker.minDate = minDate.timeInMillis
                    datePicker.show()
                }
        ){
            OutlinedTextField(
                value = birthdate,
                onValueChange = { birthdate = it },
                readOnly = true,
                enabled = false,
                label = { Text(text = "Birthdate", color = Color.DarkGray) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = "Birthdate",
                        tint = appColor
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = appColor,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = appColor,
                    focusedTextColor = Color.DarkGray
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = address,
            onValueChange = {
                address = it
            },
            label = { Text(text = "Address", color = Color.DarkGray) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.AddLocation,
                    contentDescription = "Address",
                    tint = appColor
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = appColor,
                unfocusedBorderColor = Color.Gray,
                cursorColor = appColor,
                focusedTextColor = Color.DarkGray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = country,
            onValueChange = {
                country = it
            },
            label = { Text(text = "Country", color = Color.DarkGray) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Public,
                    contentDescription = "Country",
                    tint = appColor
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = appColor,
                unfocusedBorderColor = Color.Gray,
                cursorColor = appColor,
                focusedTextColor = Color.DarkGray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = {
                phoneNumber = it
            },
            label = { Text(text = "Phone Number", color = Color.DarkGray) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Phone Number",
                    tint = appColor
                )
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = appColor,
                unfocusedBorderColor = Color.Gray,
                cursorColor = appColor,
                focusedTextColor = Color.DarkGray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = receiveEmails,
                onCheckedChange = { receiveEmails = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = appColor,
                    uncheckedColor = Color.Gray,
                    checkmarkColor = Color.White
                )
            )
            Text("Quieres recibir correos?")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                authViewModel.signup(
                    User(
                        id = "",
                        username = username,
                        email = email,
                        birthdate = birthdate,
                        address = address,
                        country = country,
                        phoneNumber = phoneNumber.toInt(),
                        receiveEmails = receiveEmails
                    ),
                    password
                )
            },
            enabled = authState.value != AuthState.Loading,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = appColor,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Registrarse", fontSize = 16.sp, color = Color.Gray)
        }
        TextButton(onClick = {
            navController.navigate("login")
        }) {
            Text("Iniciar Sesión")
        }


    }


}