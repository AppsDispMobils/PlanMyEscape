package com.example.PlanMyEscape.data

import androidx.room.Embedded
import androidx.room.Relation
import com.example.PlanMyEscape.data.local.entity.ImageEntity
import com.example.PlanMyEscape.data.local.entity.TripEntity

data class TripWithImages (
    @Embedded val trip : TripEntity,
            @Relation(
                parentColumn = "id",
                entityColumn = "tripId"
            )
    val images: List<ImageEntity>
)
