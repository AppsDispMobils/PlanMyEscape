package com.example.PlanMyEscape.domain.model

data class Room(
    val id: String,
    val roomType: String,
    val price: Float,
    val images: List<String>
)