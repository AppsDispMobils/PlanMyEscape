package com.example.sprint01.domain.model

//Data travel Object for Trip

data class Trip(
    val Id: Int,
    val userId: Int,
    val startDate: Int,
    val endDate: Int,
    val destination: String,
    val itineraryItems: List<ItineraryItem> = emptyList()
)
