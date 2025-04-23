package com.example.PlanMyEscape.data.local.mapper

import com.example.PlanMyEscape.data.local.entity.ItineraryItemEntity
import com.example.PlanMyEscape.data.local.entity.TripEntity
import com.example.PlanMyEscape.data.local.entity.UserEntity
import com.example.PlanMyEscape.domain.model.ItineraryItem
import com.example.PlanMyEscape.domain.model.Trip
import com.example.PlanMyEscape.domain.model.User


fun Trip.toEntity(): TripEntity =
    TripEntity(
        id = id, destination = destination,
        startDate = startDate, endDate = endDate,
        userId = userId)

fun ItineraryItem.toEntity(): ItineraryItemEntity =
    ItineraryItemEntity(
        id = id, title = title, tripId = tripId,
        description = description, date = date,
        startTime = startTime, endTime = endTime
    )

fun TripEntity.toDomain(itineraryItems: List<ItineraryItem> ): Trip =
    Trip(
        id = id, destination = destination,
        startDate = startDate, endDate = endDate,
        userId = userId, itineraryItems = itineraryItems
    )

fun ItineraryItemEntity.toDomain(): ItineraryItem =
    ItineraryItem(
        id = id, title = title, tripId = tripId,
        description = description, date = date,
        startTime = startTime, endTime = endTime
    )

fun User.toEntity(): UserEntity =
    UserEntity(
        id = id, email = email, username = username,
        birthdate = birthdate, address = address,
        country = country, phoneNumber = phoneNumber,
        receiveEmails = receiveEmails
    )

fun UserEntity.toDomain(): User =
    User(
        id = id, email = email, username = username,
        birthdate = birthdate, address = address,
        country = country, phoneNumber = phoneNumber,
        receiveEmails = receiveEmails
    )