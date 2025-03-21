package com.example.sprint01.data

import android.content.Context
import android.content.SharedPreferences
import com.example.sprint01.ui.utils.LanguageChangeUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefsManager @Inject constructor(
    private val preferences: SharedPreferences,
    @ApplicationContext private val context: Context
) {
    val languageChangeUtils by lazy {
        LanguageChangeUtils()
    }



    var userLanguage: String?
        get() = preferences.getString("user_language", "en")
        set(value) {
            preferences.edit().putString("user_language", value).apply()

            languageChangeUtils.changeLanguage(context, value?:"en")
        }

   }