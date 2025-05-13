package com.example.PlanMyEscape.data.remote.dto

data class RoomDto(
    val id: String,
    val room_type: String,
    val price: Float,
    val images: List<String>
)