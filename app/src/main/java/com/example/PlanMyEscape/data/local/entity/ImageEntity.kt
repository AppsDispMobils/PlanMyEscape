package com.example.PlanMyEscape.data.local.entity

import androidx.room.*

@Entity(
    tableName = "image",
    foreignKeys = [
        ForeignKey(
            entity = TripEntity::class,
            parentColumns = ["id"],
            childColumns = ["tripId"],
            onDelete = ForeignKey.CASCADE       // si borras un viaje caen sus fotos
        )
    ],
    indices = [Index("tripId")]
)
data class ImageEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tripId: Int,
    val uri: String
)