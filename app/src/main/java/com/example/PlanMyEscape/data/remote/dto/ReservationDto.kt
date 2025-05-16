package com.example.PlanMyEscape.data.remote.dto


data class ReservationDto(
    val id: String,
    val hotel_id: String,
    val room_id: String,
    val start_date: String,
    val end_date: String,
    val guest_name: String,
    val guest_email: String,
    val hotel: HotelDto,
    val room:  RoomDto
)
