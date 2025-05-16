package com.example.PlanMyEscape.domain.repository

import com.example.PlanMyEscape.domain.model.Hotel
import com.example.PlanMyEscape.domain.model.Reservation
import com.example.PlanMyEscape.domain.model.ReserveRequest

interface HotelRepository {

    suspend fun getHotels(groupId: String): List<Hotel>

    suspend fun reserve(groupId: String, request: ReserveRequest): Reservation



}