package com.example.sprint01.data.local.mapper

import com.example.sprint01.data.local.entity.ItineraryItemEntity
import com.example.sprint01.data.local.entity.TripEntity
import com.example.sprint01.domain.model.ItineraryItem
import com.example.sprint01.domain.model.Trip


fun Trip.toEntity(): TripEntity =
    TripEntity(
        id = id, destination = destination,
        startDate = startDate, endDate = endDate)

fun ItineraryItem.toEntity(): ItineraryItemEntity =
    ItineraryItemEntity(
        id = id, title = title, tripId = tripId,
        description = description, date = date
    )

fun TripEntity.toDomain(itineraryItems: List<ItineraryItem> ): Trip =
    Trip(
        id = id, destination = destination,
        startDate = startDate, endDate = endDate,
        itineraryItems = itineraryItems
    )

fun ItineraryItemEntity.toDomain(): ItineraryItem =
    ItineraryItem(
        id = id, title = title, tripId = tripId,
        description = description, date = date
    )