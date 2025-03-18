package com.example.sprint01.domain.repository

import com.example.sprint01.domain.model.ItineraryItem

interface ItineraryItemRepository {

    fun addItineraryItem(itineraryItem: ItineraryItem)
    fun deleteItineraryItem(itineraryItemId: Int)
    fun updateItineraryItem(itineraryItem: ItineraryItem)

}