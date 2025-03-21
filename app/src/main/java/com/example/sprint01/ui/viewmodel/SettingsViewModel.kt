package com.example.sprint01.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.sprint01.data.SharedPrefsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val sharedPrefsManager: SharedPrefsManager
) : ViewModel() {

    // Variables de estado inicializadas con los valores guardados.
    var language by mutableStateOf(sharedPrefsManager.userLanguage ?: "es")
        private set

    fun updateLanguage(newLanguage: String) {
        sharedPrefsManager.userLanguage = newLanguage
        language = newLanguage
    }

}