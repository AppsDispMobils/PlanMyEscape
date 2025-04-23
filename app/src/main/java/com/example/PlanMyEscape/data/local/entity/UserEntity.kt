package com.example.PlanMyEscape.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val email: String,
    val username: String,
    val birthdate: String,
    val address: String,
    val country: String,
    val phoneNumber: Int,
    val receiveEmails: Boolean
)