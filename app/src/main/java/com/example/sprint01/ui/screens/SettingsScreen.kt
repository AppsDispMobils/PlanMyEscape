package com.example.sprint01.ui.screens

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sprint01.R
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)

    var selectedIndex by remember { mutableStateOf(0) }
    // 🔹 Cargar el idioma guardado en SharedPreferences
    var selectedLanguage by remember {
        mutableStateOf(sharedPreferences.getString("language", "es") ?: "es")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = stringResource(id = R.string.settings), modifier = Modifier.fillMaxWidth(),
                            fontSize = 24.sp, textAlign = TextAlign.Center)
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedIndex = selectedIndex,
                navController
            )
        },
        content = { padding ->
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // 🔹 Texto "Settings" centrado


                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(20.dp))

                // Opción para cambiar el color de fondo
                Text(
                    text = stringResource(id = R.string.change_background),
                    fontSize = 18.sp,
                    modifier = Modifier
                        .clickable { /* TODO: Implementar */ }
                        .fillMaxWidth()
                        .padding(10.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Opción para cambiar la contraseña
                Text(
                    text = stringResource(id = R.string.change_password),
                    fontSize = 18.sp,
                    modifier = Modifier
                        .clickable { /* TODO: Implementar */ }
                        .fillMaxWidth()
                        .padding(10.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Navegación a About Us
                Text(
                    text = stringResource(id = R.string.about_us),
                    fontSize = 18.sp,
                    modifier = Modifier
                        .clickable { navController.navigate("aboutUs") }
                        .fillMaxWidth()
                        .padding(10.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Navegación a Terms and Conditions
                Text(
                    text = stringResource(id = R.string.terms_and_condi),
                    fontSize = 18.sp,
                    modifier = Modifier
                        .clickable { navController.navigate("termsConditions") }
                        .fillMaxWidth()
                        .padding(10.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 🔹 Botón para cambiar de idioma
                Button(
                    onClick = {
                        val newLanguage = if (selectedLanguage == "es") "es" else "en"
                        selectedLanguage = newLanguage

                        // 🔹 Guardamos el idioma en SharedPreferences
                        sharedPreferences.edit().putString("language", newLanguage).apply()

                        // 🔹 Aplicamos el cambio de idioma y reiniciamos la actividad
                        LocaleHelper.setLocale(context, newLanguage)
                        restartApp(context)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = if (selectedLanguage == "en") "Cambiar a Ingles " else "Change to English")
                }

                Spacer(modifier = Modifier.height(20.dp))

                // 🔹 Botón para volver a la Home Screen
                Button(
                    onClick = { navController.navigate("home") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.volver))
                }
            }
        }
    )
}

// 🔹 Utilidad para cambiar el idioma y actualizar el contexto
object LocaleHelper {
    fun setLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.createConfigurationContext(config)
        } else {
            @Suppress("DEPRECATION")
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        }
    }
}

fun setAppLocale(language: String, context: Context) {
    val locale = Locale(language)
    Locale.setDefault(locale)

    val config = context.resources.configuration
    config.setLocale(locale)
    config.setLayoutDirection(locale)

    context.createConfigurationContext(config)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
}

// 🔹 Función para reiniciar la aplicación y aplicar el nuevo idioma
fun restartApp(context: Context) {
    val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
    intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
}