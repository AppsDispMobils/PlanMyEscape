package com.example.sprint01.domain.repository

import com.example.sprint01.domain.model.ItineraryItem
import com.example.sprint01.domain.model.Trip

interface TripRepository {

    suspend fun getTrips(): List<Trip>
    suspend fun addTrip(trip: Trip)
    suspend fun deleteTrip(tripId: Int)
    suspend fun updateTrip(trip: Trip)
    suspend fun validateTripDestination(tripDestination: String): Int

    suspend fun getItineraryItemsfromTrip(tripId: Int): List<ItineraryItem>
    suspend fun addItineraryItem(itineraryItem: ItineraryItem)
    suspend fun deleteItineraryItem(itineraryItemId: Int)
    suspend fun updateItineraryItem(itineraryItem: ItineraryItem)

}