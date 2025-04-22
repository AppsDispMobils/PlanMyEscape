package com.example.sprint01.domain.model

data class User(
    val id: String,
    val email: String,
    val username: String,
    val birthdate: String,
    val address: String,
    val country: String,
    val phoneNumber: Int,
    val receiveEmails: Boolean
)
