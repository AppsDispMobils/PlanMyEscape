package com.example.sprint01.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sprint01.data.local.dao.ItineraryItemDao
import com.example.sprint01.data.local.dao.TripDao
import com.example.sprint01.data.local.entity.ItineraryItemEntity
import com.example.sprint01.data.local.entity.TripEntity


@Database(
    entities = [TripEntity::class, ItineraryItemEntity::class],
    version = 1,
    exportSchema = false
)

abstract class PlanMyEscapeDatabase : RoomDatabase() {
    abstract fun tripDao(): TripDao
    abstract fun itineraryItemDao(): ItineraryItemDao
}