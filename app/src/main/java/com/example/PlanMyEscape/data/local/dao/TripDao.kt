package com.example.PlanMyEscape.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.PlanMyEscape.data.local.entity.TripEntity


@Dao
interface TripDao {
    @Query("SELECT * FROM trips WHERE userId = :userId ORDER BY startDate ASC")
    suspend fun getTrips(userId: String): List<TripEntity>

    @Insert
    suspend fun addTrip(trip: TripEntity)

    @Query("DELETE FROM trips WHERE id = :tripId")
    suspend fun deleteTrip(tripId: Int)

    @Update
    suspend fun updateTrip(trip: TripEntity)

    @Query("SELECT count(*) FROM trips WHERE destination = :tripDestination AND userId = :userId")
    suspend fun validateTripDestination(tripDestination: String, userId: String): Int
}
