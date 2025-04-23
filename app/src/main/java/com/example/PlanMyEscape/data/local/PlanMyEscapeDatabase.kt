package com.example.PlanMyEscape.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.PlanMyEscape.data.local.dao.ItineraryItemDao
import com.example.PlanMyEscape.data.local.dao.TripDao
import com.example.PlanMyEscape.data.local.dao.UserDao
import com.example.PlanMyEscape.data.local.entity.ItineraryItemEntity
import com.example.PlanMyEscape.data.local.entity.TripEntity
import com.example.PlanMyEscape.data.local.entity.UserEntity


@Database(
    entities = [TripEntity::class, ItineraryItemEntity::class, UserEntity::class],
    version = 1,
    exportSchema = false
)

abstract class PlanMyEscapeDatabase : RoomDatabase() {
    abstract fun tripDao(): TripDao
    abstract fun itineraryItemDao(): ItineraryItemDao
    abstract fun userDao(): UserDao
}