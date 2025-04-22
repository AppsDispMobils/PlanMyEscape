package com.example.sprint01.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.sprint01.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Insert
    suspend fun addUser(user: UserEntity)

    @Query("SELECT COUNT(*) FROM users WHERE username = :username")
    suspend fun validateUsername(username: String) : Int
}