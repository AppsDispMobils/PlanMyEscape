package com.example.sprint01.domain.repository

import android.util.Log
import com.example.sprint01.domain.model.ItineraryItem
import com.example.sprint01.domain.model.Trip
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TripRepositoryImpl @Inject constructor() : TripRepository {

    private val trips = mutableListOf<Trip>()
    private val itineraryItems = mutableListOf<ItineraryItem>()

    override fun getTrips(): List<Trip> {
        return trips.map { trip ->
            trip.copy(itineraryItems = itineraryItems.filter { it.tripId == trip.Id })
        }

    }

    override fun addTrip(trip: Trip) {
        val newTrip = trip.copy(Id = trips.size + 1)
        trips.add(newTrip)
    }

    override fun deleteTrip(tripId: Int) {
        trips.removeAll { it.Id == tripId }
    }

    override fun updateTrip(trip: Trip) {
        val index = trips.indexOfFirst { it.Id == trip.Id }
        if (index != -1) {
            trips[index] = trip
        }

    }

    override fun addItineraryItem(itineraryItem: ItineraryItem) {
        val newItineraryItem = itineraryItem.copy(Id = itineraryItems.size + 1)
        itineraryItems.add(newItineraryItem)
    }

    override fun updateItineraryItem(itineraryItem: ItineraryItem) {
        val index = itineraryItems.indexOfFirst { itineraryItem.Id == it.Id }
        if (index != -1) {
            itineraryItems[index] = itineraryItem
        }
    }

    override fun deleteItineraryItem(itineraryItemId: Int) {
        itineraryItems.removeAll { it.Id == itineraryItemId }
    }

    override fun getItineraryItemsfromTrip(tripId: Int): List<ItineraryItem> {
        return itineraryItems.filter { it.tripId == tripId }
    }


}