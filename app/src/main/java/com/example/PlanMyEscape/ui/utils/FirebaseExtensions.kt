package com.example.PlanMyEscape.ui.utils

// En FirebaseExtensions.kt o un archivo similar
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

// Función de extensión para enviar correo de verificación
suspend fun FirebaseUser.sendEmailVerificationAsync(): Boolean {
    return try {
        // Espera la respuesta de la tarea (espera de la operación)
        this.sendEmailVerification().await()
        true
    } catch (e: Exception) {
        false
    }
}
