package com.example.sprint01.domain.repository

import com.example.sprint01.domain.model.ItineraryItem
import com.example.sprint01.domain.model.Trip

interface TripRepository {

    fun getTrips(): List<Trip>
    fun addTrip(trip: Trip)
    fun deleteTrip(tripId: Int)
    fun updateTrip(trip: Trip)

    fun getItineraryItemsfromTrip(tripId: Int): List<ItineraryItem>

}