package com.example.sprint01.data.repository

import com.example.sprint01.data.local.dao.ItineraryItemDao
import com.example.sprint01.data.local.dao.TripDao
import com.example.sprint01.data.local.mapper.toDomain
import com.example.sprint01.data.local.mapper.toEntity
import com.example.sprint01.domain.model.ItineraryItem
import com.example.sprint01.domain.model.Trip
import com.example.sprint01.domain.repository.TripRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TripRepositoryImpl @Inject constructor(
    private val TripDao: TripDao,
    private val ItineraryItemDao: ItineraryItemDao
) : TripRepository {

    private val trips = mutableListOf<Trip>()
    private val itineraryItems = mutableListOf<ItineraryItem>()

    override suspend fun getTrips(): List<Trip> {
        val trips = TripDao.getTrips()

        return trips.map { trip ->
            val itineraryItem = ItineraryItemDao.getItineraryItemsFromTrip(trip.id).map { it.toDomain() }
            trip.toDomain(itineraryItem)
        }


        //return trips.map { trip ->
        //    trip.copy(itineraryItems = itineraryItems.filter { it.tripId == trip.id })
        //}

    }

    override suspend fun addTrip(trip: Trip) {
        val newTrip = trip.copy(id = trips.size + 1)
        val tripEntity = newTrip.toEntity()
        TripDao.addTrip(tripEntity)
    }

    override suspend fun deleteTrip(tripId: Int) {
        TripDao.deleteTrip(tripId)
    }

    override suspend fun updateTrip(trip: Trip) {
        TripDao.updateTrip(trip.toEntity())
    }

    override suspend fun addItineraryItem(itineraryItem: ItineraryItem) {
        val newItineraryItem = itineraryItem.copy(id = itineraryItems.size + 1)
        itineraryItems.add(newItineraryItem)
    }

    override suspend fun updateItineraryItem(itineraryItem: ItineraryItem) {
        val index = itineraryItems.indexOfFirst { itineraryItem.id == it.id }
        if (index != -1) {
            itineraryItems[index] = itineraryItem
        }
    }

    override suspend fun deleteItineraryItem(itineraryItemId: Int) {
        itineraryItems.removeAll { it.id == itineraryItemId }
    }

    override suspend fun getItineraryItemsfromTrip(tripId: Int): List<ItineraryItem> {
        return itineraryItems.filter { it.tripId == tripId }
    }


}