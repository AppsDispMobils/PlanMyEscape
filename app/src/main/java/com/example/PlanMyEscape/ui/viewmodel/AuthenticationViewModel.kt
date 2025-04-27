package com.example.PlanMyEscape.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.PlanMyEscape.domain.model.User
import com.example.PlanMyEscape.domain.repository.AuthenticationRepository
import com.example.PlanMyEscape.ui.utils.sendEmailVerificationAsync
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
): ViewModel()
{

    private val _authState = MutableLiveData<AuthState>()
    val authState : LiveData<AuthState> = _authState

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()


    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {//funcion que verifica si usuario sigue logueado, si lo hay o no, actualiza el authState,
        viewModelScope.launch {
            if (authenticationRepository.checkAuthStatus()) {
                _authState.value = AuthState.Authenticated
            } else {
                _authState.value = AuthState.Unauthenticated
            }
        }
    }

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }
        _authState.value = AuthState.Loading

        viewModelScope.launch {
            try {
                // Intentar iniciar sesión
                authenticationRepository.login(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val currentUser = authenticationRepository.getCurrentUser()

                            if (currentUser != null) {

                                if (currentUser.isEmailVerified) {

                                    _authState.value = AuthState.Authenticated
                                } else {
                                    // Si el correo no está verificado, cerramos sesión y notificamos al usuario
                                    _authState.value = AuthState.Error("Por favor, verifica tu correo electrónico.")
                                    authenticationRepository.logOut() // Salimos hasta que verifique el correo
                                }
                            } else {
                                _authState.value = AuthState.Error("No se pudo obtener el usuario después de iniciar sesión.")
                            }
                        } else {
                            _authState.value = AuthState.Error(task.exception?.message.toString())
                        }
                    }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Error desconocido al iniciar sesión.")
            }
        }
    }


    fun signup(user: User, password: String) {
        _authState.value = AuthState.Loading

        viewModelScope.launch {
            try {
                // Intentar registrar al usuario
                val result = authenticationRepository.signUp(user, password)

                // Si el registro es exitoso, procedemos a enviar la verificación por correo
                val currentUser = authenticationRepository.getCurrentUser()

                if (currentUser != null) {
                    // Enviar correo de verificación
                    val verifyTask = currentUser.sendEmailVerificationAsync()// Usamos `await()` para que sea suspendido
                    if (verifyTask) {
                        _authState.value = AuthState.Error("Verifica tu correo electrónico antes de iniciar sesión.")
                        authenticationRepository.logOut() // Salir hasta que verifique
                    } else {
                        _authState.value = AuthState.Error("No se pudo enviar el correo de verificación.")
                    }
                } else {
                    _authState.value = AuthState.Error("No se pudo obtener el usuario después de registro.")
                }

            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Error desconocido al registrar el usuario.")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authenticationRepository.logOut()
            _authState.value = AuthState.Unauthenticated
        }
    }

    fun recoverPassword(email: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _authState.value = AuthState.Success
                        } else {
                            _authState.value = AuthState.Error(task.exception?.localizedMessage ?: "Error desconocido")
                        }
                    }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.localizedMessage ?: "Error desconocido")
            }
        }
    }


}

sealed class AuthState{
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message : String) : AuthState()
    object Success : AuthState()
}