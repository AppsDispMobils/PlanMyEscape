package com.example.sprint01.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.sprint01.data.local.entity.ItineraryItemEntity


@Dao
interface ItineraryItemDao {
    @Query("SELECT * FROM itineraryItems where tripId = :tripId")
    suspend fun getItineraryItemsFromTrip(tripId: Int) : List<ItineraryItemEntity>

    @Insert
    suspend fun addItineraryItem(itineraryItemEntity: ItineraryItemEntity)

    @Query("DELETE FROM itineraryItems WHERE id = :itineraryItemId")
    suspend fun deleteItineraryItem(itineraryItemId: Int)

    @Update
    suspend fun updateItineraryItem(itineraryItemEntity: ItineraryItemEntity)
}


