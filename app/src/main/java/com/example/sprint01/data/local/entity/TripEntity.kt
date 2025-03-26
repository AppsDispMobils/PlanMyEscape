package com.example.sprint01.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sprint01.domain.model.ItineraryItem

@Entity(tableName = "trips")
data class TripEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val destination: String,
    val startDate: String,
    val endDate: String,
    val itineraryItem: List<ItineraryItemEntity> = emptyList()
)
