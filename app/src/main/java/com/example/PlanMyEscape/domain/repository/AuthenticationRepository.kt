package com.example.PlanMyEscape.domain.repository

import com.example.PlanMyEscape.domain.model.User
import com.example.PlanMyEscape.ui.viewmodel.AuthState
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface AuthenticationRepository {

    suspend fun login(email: String, password: String): Task<AuthResult>
    suspend fun logOut()
    suspend fun signUp(user: User, password: String): AuthResult
    suspend fun checkAuthStatus(): Boolean
    suspend fun getCurrentId(): String
}