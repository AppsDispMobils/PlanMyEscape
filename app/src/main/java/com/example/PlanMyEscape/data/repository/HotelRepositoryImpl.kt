package com.example.PlanMyEscape.data.repository

import com.example.PlanMyEscape.data.remote.api.HotelApiService
import com.example.PlanMyEscape.data.remote.mapper.toDomain
import com.example.PlanMyEscape.data.remote.mapper.toDto
import com.example.PlanMyEscape.domain.model.Hotel
import com.example.PlanMyEscape.domain.model.Reservation
import com.example.PlanMyEscape.domain.model.ReserveRequest
import com.example.PlanMyEscape.domain.repository.HotelRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HotelRepositoryImpl @Inject constructor(
    override suspend fun getHotels(groupId: String): List<Hotel> =
        api.getHotels(groupId).map { it.toDomain() }

    override suspend fun getAvailability(
        groupId: String,
        start: String,
        end: String,
        hotelId: String?,
        city: String?

    override suspend fun reserve(groupId: String, request: ReserveRequest): Reservation =
        api.reserveRoom(groupId, request.toDto()).reservation.toDomain()

    override suspend fun cancel(groupId: String, request: ReserveRequest): String =
        api.cancelReservation(groupId, request.toDto()).message

        api.getGroupReservations(groupId, guestEmail).reservations.map { it.toDomain() }



}