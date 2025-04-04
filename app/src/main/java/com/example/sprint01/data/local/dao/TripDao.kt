package com.example.sprint01.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.sprint01.data.local.entity.TripEntity


@Dao
interface TripDao {
    @Query("SELECT * FROM trips ORDER BY startDate ASC")
    suspend fun getTrips(): List<TripEntity>

    @Insert
    suspend fun addTrip(trip: TripEntity)

    @Query("DELETE FROM trips WHERE id = :tripId")
    suspend fun deleteTrip(tripId: Int)

    @Update
    suspend fun updateTrip(trip: TripEntity)

    @Query("SELECT count(*) FROM trips where destination = :tripDestination")
    suspend fun validateTripDestination(tripDestination: String): Int
}
