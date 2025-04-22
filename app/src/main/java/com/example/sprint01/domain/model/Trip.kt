package com.example.sprint01.domain.model

//Data travel Object for Trip

data class Trip(
    val id: Int = 0,
    val destination: String,
    val startDate: String,
    val endDate: String,
    val userId: String,
    val itineraryItems: List<ItineraryItem> = emptyList()
)
