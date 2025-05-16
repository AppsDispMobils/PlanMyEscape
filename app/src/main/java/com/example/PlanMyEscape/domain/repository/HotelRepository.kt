package com.example.PlanMyEscape.domain.repository

import com.example.PlanMyEscape.domain.model.Hotel
import com.example.PlanMyEscape.domain.model.Reservation
import com.example.PlanMyEscape.domain.model.ReserveRequest

interface HotelRepository {

    suspend fun getHotels(groupId: String): List<Hotel>

    suspend fun getAvailability(groupId: String, start: String, end: String, hotelId: String? = null, city: String? = null): List<Hotel>

    suspend fun reserve(groupId: String, request: ReserveRequest): Reservation

    suspend fun cancel(groupId: String, request: ReserveRequest): String

    suspend fun getGroupReservations(groupId: String, guestEmail: String? = null): List<Reservation>

    suspend fun getReservationById(reservationId: String): Reservation
    suspend fun cancelById(reservationId: String): Reservation
}