package com.example.sprint01.data.repository

import android.util.Log
import com.example.sprint01.data.local.dao.ItineraryItemDao
import com.example.sprint01.data.local.dao.TripDao
import com.example.sprint01.data.local.mapper.toDomain
import com.example.sprint01.data.local.mapper.toEntity
import com.example.sprint01.domain.model.ItineraryItem
import com.example.sprint01.domain.model.Trip
import com.example.sprint01.domain.repository.AuthenticationRepository
import com.example.sprint01.domain.repository.TripRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TripRepositoryImpl @Inject constructor(
    private val TripDao: TripDao,
    private val ItineraryItemDao: ItineraryItemDao,
    private val authenticationRepository: AuthenticationRepository
) : TripRepository {


    override suspend fun getTrips(): List<Trip> {
        val userId = authenticationRepository.getCurrentId()
        val trips = TripDao.getTrips(userId)
        Log.d("Database", "Getting all trips from DB")
        return trips.map { trip ->
            val itineraryItem = ItineraryItemDao.getItineraryItemsFromTrip(trip.id).map { it.toDomain() }
            trip.toDomain(itineraryItem)
        }
    }

    override suspend fun addTrip(trip: Trip) {
        Log.d("Database", "Adding trip in DB")
        TripDao.addTrip(trip.toEntity())
    }


    override suspend fun deleteTrip(tripId: Int) {
        Log.d("Database", "Deleting trip in DB")
        TripDao.deleteTrip(tripId)
    }

    override suspend fun updateTrip(trip: Trip) {
        Log.d("Database", "Updating trip in DB")
        TripDao.updateTrip(trip.toEntity())
    }

    override suspend fun validateTripDestination(tripDestination: String): Int {
        val userId = authenticationRepository.getCurrentId()
        return TripDao.validateTripDestination(tripDestination, userId)
    }


    override suspend fun addItineraryItem(itineraryItem: ItineraryItem) {
        Log.d("Database", "Adding itinerary item to DB")
        ItineraryItemDao.addItineraryItem(itineraryItem.toEntity())
    }

    override suspend fun updateItineraryItem(itineraryItem: ItineraryItem) {
        Log.d("Database", "Updating itinerary item in DB")
        ItineraryItemDao.updateItineraryItem(itineraryItem.toEntity())
    }

    override suspend fun deleteItineraryItem(itineraryItemId: Int) {
        Log.d("Database", "Deleting itinerary item in DB")
        ItineraryItemDao.deleteItineraryItem(itineraryItemId)
    }

    override suspend fun getItineraryItemsfromTrip(tripId: Int): List<ItineraryItem> {
        Log.d("Database", "Getting all itinerary items from DB")
        return ItineraryItemDao.getItineraryItemsFromTrip(tripId).map { it.toDomain()}
    }
}