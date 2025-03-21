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
        Log.d("Trip", "Showing all trips")
        return trips.map { trip ->
            trip.copy(itineraryItems = itineraryItems.filter { it.tripId == trip.Id })
        }

    }

    override fun addTrip(trip: Trip) {
        val newTrip = trip.copy(Id = trips.size + 1)
        trips.add(newTrip)
        Log.d("Trip", "Added new trip: ${newTrip.destination}")
    }

    override fun deleteTrip(tripId: Int) {
        Log.d("Trip", "Deleting trip...")
        trips.removeAll { it.Id == tripId }
    }

    override fun updateTrip(trip: Trip) {
        val index = trips.indexOfFirst { it.Id == trip.Id }
        if (index != -1) {
            trips[index] = trip
        }
        Log.d("Trip", "Updated ${trips[index].destination}")

    }

    override fun addItineraryItem(itineraryItem: ItineraryItem) {
        val newItineraryItem = itineraryItem.copy(Id = itineraryItems.size + 1)
        itineraryItems.add(newItineraryItem)
        Log.d("Itinerary", "Added new itinerary item:${newItineraryItem.title}")
    }

    override fun updateItineraryItem(itineraryItem: ItineraryItem) {
        val index = itineraryItems.indexOfFirst { itineraryItem.Id == it.Id }
        if (index != -1) {
            itineraryItems[index] = itineraryItem
        }
        Log.d("Itinerary", "Updated ${itineraryItems[index].title} itinerary item")
    }

    override fun deleteItineraryItem(itineraryItemId: Int) {
        Log.d("Itinerary", "Deleting itinerary item...")
        itineraryItems.removeAll { it.Id == itineraryItemId }
    }

    override fun getItineraryItemsfromTrip(tripId: Int): List<ItineraryItem> {
        Log.d("Itinerary", "Showing all itinerary items")
        return itineraryItems.filter { it.tripId == tripId }
    }


}