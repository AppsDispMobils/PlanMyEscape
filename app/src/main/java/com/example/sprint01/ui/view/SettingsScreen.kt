package com.example.sprint01.ui.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.sprint01.R
import com.example.sprint01.ui.view.BottomNavigationBar
import com.example.sprint01.ui.viewmodel.SettingsViewModel
import android.util.Log


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)

    // Configuración de idioma
    val language = viewModel.language // Obtenemos el idioma desde el ViewModel
    val isDarkTheme = viewModel.isDarkTheme

    Log.d("SettingsScreen", "Idioma actual: $language, Tema oscuro: $isDarkTheme")

    Scaffold(
        topBar = {
            TopNavigationBar(
                navController = navController,
                title = stringResource(id = R.string.settings)
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedIndex = 0,
                navController
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // Opciones de configuración
                Spacer(modifier = Modifier.height(10.dp))

                // Opción para cambiar la contraseña
                Text(
                    text = stringResource(id = R.string.change_password),
                    fontSize = 18.sp,
                    modifier = Modifier
                        .clickable {
                            Log.d("SettingsScreen", "Opción de cambiar contraseña seleccionada.")
                            /* TODO: Implementar */ }
                        .fillMaxWidth()
                        .padding(10.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(id = R.string.about_us),
                    fontSize = 18.sp,
                    modifier = Modifier
                        .clickable {
                            Log.d("SettingsScreen", "Navegar a Sobre Nosotros")
                            navController.navigate("aboutUs") }
                        .fillMaxWidth()
                        .padding(10.dp)

                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(id = R.string.terms_and_condi),
                    fontSize = 18.sp,
                    modifier = Modifier
                        .clickable {
                            Log.d("SettingsScreen", "Navegar a Términos y Condiciones")
                            navController.navigate("termsConditions") }
                        .fillMaxWidth()
                        .padding(10.dp)

                )
                // Cambiar idioma
                LanguageDropdown(
                    selectedLanguage = language,
                    onLanguageSelected = { newLang ->
                        Log.d("SettingsScreen", "Idioma seleccionado: $newLang")
                        viewModel.updateLanguage(newLang) },
                    availableLanguages = listOf("en", "es","pt")
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text(stringResource(id = R.string.Dark_Theme))
                    Switch(
                        checked = isDarkTheme as Boolean,
                        onCheckedChange = {
                            Log.d("SettingsScreen", "Tema oscuro cambiado")
                            viewModel.updateDarkTheme(it)}
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

            }
        }
    )
}

@Composable
fun LanguageDropdown(
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit,
    availableLanguages: List<String>
) {
    var expanded by remember { mutableStateOf(false) }
    val languageDisplay = when (selectedLanguage) {
        "es" -> "Español"
        "en" -> "English"
        "pt" -> "Português"
        else -> selectedLanguage
    }

    OutlinedTextField(
        value = languageDisplay,
        onValueChange = {},
        label = { Text("Idioma") },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Mostrar idiomas")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier.fillMaxWidth()
    ) {
        availableLanguages.forEach { lang ->
            val langName = when (lang) {
                "es" -> "Español"
                "en" -> "English"
                "pt" -> "Português"
                else -> lang
            }
            DropdownMenuItem(
                text = { Text(langName) },
                onClick = {
                    Log.d("SettingsScreen", "Idioma seleccionado en el dropdown: $lang")
                    onLanguageSelected(lang) // Llamamos a la función para seleccionar el idioma
                    expanded = false
                }
            )
        }
    }
}


