package com.example.PlanMyEscape.data.repository

import com.example.PlanMyEscape.data.local.dao.UserDao
import com.example.PlanMyEscape.data.local.mapper.toEntity
import com.example.PlanMyEscape.domain.model.User
import com.example.PlanMyEscape.domain.repository.AuthenticationRepository
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userDao: UserDao
): AuthenticationRepository
{
    override suspend fun login(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email, password)
    }

    override suspend fun logOut() {
        firebaseAuth.signOut()
    }

    override suspend fun signUp(user: User, password: String): AuthResult {
        if(userDao.validateUsername(user.username) != 0) {
            throw Exception("Usuario ya en uso")
        }

        val result = try {
            firebaseAuth.createUserWithEmailAndPassword(user.email, password).await()
        } catch(e : Exception) {
            throw Exception("Error creando usuario")
        }

        val uid = result.user?.uid.toString()
        val userWithId = user.copy(id = uid)

        try {
            userDao.addUser(userWithId.toEntity())
        } catch(e : Exception) {
            throw Exception("Error añadiendo usuario a la BD")
        }

        return result
    }

    override suspend fun checkAuthStatus(): Boolean {
        if(firebaseAuth.currentUser == null) {
            return false
        } else return true
    }

    override suspend fun getCurrentId(): String {
        return firebaseAuth.currentUser?.uid.toString()
    }

}