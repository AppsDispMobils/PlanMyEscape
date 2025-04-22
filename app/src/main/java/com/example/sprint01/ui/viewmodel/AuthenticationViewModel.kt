package com.example.sprint01.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sprint01.domain.model.User
import com.example.sprint01.domain.repository.AuthenticationRepository
import com.google.android.gms.tasks.Task
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

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        viewModelScope.launch {
            if (authenticationRepository.checkAuthStatus()) {
                _authState.value = AuthState.Authenticated
            } else {
                _authState.value = AuthState.Unauthenticated
            }
        }
    }

    fun login(email: String, password: String) {
        if(email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }
        _authState.value = AuthState.Loading
        viewModelScope.launch {
           authenticationRepository.login(email, password)
               .addOnCompleteListener { task ->
                   if(task.isSuccessful){
                       _authState.value = AuthState.Authenticated
                   } else {
                       _authState.value = AuthState.Error(task.exception?.message.toString())
                   }
               }
        }
    }

    fun signup(user: User, password: String) {
        _authState.value = AuthState.Loading

        viewModelScope.launch {
            try {
                authenticationRepository.signUp(user, password)
                _authState.value = AuthState.Authenticated
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Error desconocido")
            }

        }

    }

    fun logout() {
        viewModelScope.launch {
            authenticationRepository.logOut()
            _authState.value = AuthState.Unauthenticated
        }
    }


}

sealed class AuthState{
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message : String) : AuthState()
}