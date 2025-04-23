package com.example.PlanMyEscape.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.PlanMyEscape.domain.model.ItineraryItem
import com.example.PlanMyEscape.domain.repository.TripRepository
import com.example.PlanMyEscape.data.repository.TripRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TripDetailsViewModel @Inject constructor(
    private val repository: TripRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val tripId: Int = savedStateHandle["tripId"] ?: 0

    private val _itineraryItems = mutableStateListOf<ItineraryItem>()
    val itineraryItems = _itineraryItems

    init {
        loadItineraryItems()
    }

    private fun loadItineraryItems() {
        itineraryItems.clear()
        viewModelScope.launch {
            itineraryItems.addAll(repository.getItineraryItemsfromTrip(tripId))
            Log.d("Itinerary", "Showing all itinerary items")
        }

    }

    fun addItitneraryItem(itineraryItem: ItineraryItem) {
        viewModelScope.launch {
            repository.addItineraryItem(itineraryItem)
            Log.d("Itinerary", "Added new itinerary item:${itineraryItem.title}")

            loadItineraryItems()
        }
    }

    fun updateItineraryItem(itineraryItem: ItineraryItem) {
        viewModelScope.launch {
            repository.updateItineraryItem(itineraryItem)
            Log.d("Itinerary", "Updated ${itineraryItem.title} itinerary item")
            loadItineraryItems()
        }
    }

    fun deleteItineraryItem(itineraryItemId: Int) {

        viewModelScope.launch {
            repository.deleteItineraryItem(itineraryItemId)
            Log.d("Itinerary", "Deleting itinerary item...")
            loadItineraryItems()
        }
    }
}