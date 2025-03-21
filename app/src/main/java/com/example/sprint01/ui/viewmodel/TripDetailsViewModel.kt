package com.example.sprint01.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sprint01.domain.model.ItineraryItem
import com.example.sprint01.domain.repository.TripRepository
import com.example.sprint01.domain.repository.TripRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TripDetailsViewModel @Inject constructor(
    private val repository: TripRepository = TripRepositoryImpl(),
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
        itineraryItems.addAll(repository.getItineraryItemsfromTrip(tripId))
        Log.d("Itinerary", "Showing all itinerary items")
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
        repository.deleteItineraryItem(itineraryItemId)
        Log.d("Itinerary", "Deleting itinerary item...")
        loadItineraryItems()

    }
}