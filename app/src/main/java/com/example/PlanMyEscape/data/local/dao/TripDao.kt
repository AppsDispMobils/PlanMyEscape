package com.example.PlanMyEscape.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.PlanMyEscape.data.TripWithImages
import com.example.PlanMyEscape.data.local.entity.ImageEntity
import com.example.PlanMyEscape.data.local.entity.TripEntity
import kotlinx.coroutines.flow.Flow


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

    @Transaction
    @Query("SELECT * FROM trips")
    fun getTripsWithImages(): Flow<List<TripWithImages>>

    @Insert
    suspend fun insertImage(image: ImageEntity)

    @Query("DELETE FROM image WHERE uri = :uri")
    suspend fun deleteImageByUri(uri: String)
}
