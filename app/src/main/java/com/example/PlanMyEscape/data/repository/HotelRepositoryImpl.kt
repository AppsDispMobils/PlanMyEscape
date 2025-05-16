package com.example.PlanMyEscape.data.repository

import android.util.Log
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
    private val api: HotelApiService //crear el servicio q accede a la API en /remote/api
): HotelRepository
{
    override suspend fun getHotels(groupId: String): List<Hotel> =
        api.getHotels(groupId).map { it.toDomain() }


    override suspend fun getAvailability(
        groupId: String,
        start: String,
        end: String,
        hotelId: String?,
        city: String?
    ): List<Hotel> = api.getAvailability(groupId, start, end, hotelId, city).available_hotels.map { it.toDomain() }


    override suspend fun reserve(groupId: String, request: ReserveRequest): Reservation =
        api.reserveRoom(groupId, request.toDto()).reservation.toDomain()


    override suspend fun cancel(groupId: String, request: ReserveRequest): String =
        api.cancelReservation(groupId, request.toDto()).message


    override suspend fun getGroupReservations(groupId: String, guestEmail: String?): List<Reservation> =
        api.getGroupReservations(groupId, guestEmail).reservations.map { it.toDomain() }


    override suspend fun getReservationById(reservationId: String): Reservation =
        api.getReservationById(reservationId).toDomain()


    override suspend fun cancelById(reservationId: String): Reservation =
        api.deleteReservationById(reservationId).toDomain()

}