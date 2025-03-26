package com.example.sprint01.domain.model

//Data Travel Object for ItineraryItem

data class ItineraryItem(
    val id: Int = 0,
    val title: String,
    val tripId: Int,
    val description: String,
    val date: String
)
