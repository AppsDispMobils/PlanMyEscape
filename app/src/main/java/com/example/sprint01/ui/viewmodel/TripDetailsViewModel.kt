package com.example.sprint01.ui.viewmodel

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
    val repository: TripRepository = TripRepositoryImpl(),
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val tripId: Int = savedStateHandle["tripId"] ?: 0

    private val _itineraryItems = mutableListOf<ItineraryItem>()
    public val itineraryItems = _itineraryItems

    init {
        loadItineraryItems()
    }

    private fun loadItineraryItems() {
        _itineraryItems.clear()
        _itineraryItems.addAll(repository.getItineraryItemsfromTrip(tripId))
    }

    fun addItitneraryItem(itineraryItem: ItineraryItem) {
        viewModelScope.launch {
            repository.addItineraryItem(itineraryItem)
            loadItineraryItems()
        }
    }

    fun updateItineraryItem(itineraryItem: ItineraryItem) {
        viewModelScope.launch {
            repository.updateItineraryItem(itineraryItem)
            loadItineraryItems()
        }
    }

    fun deleteItineraryItem(itineraryItemId: Int) {
        viewModelScope.launch {
            repository.deleteItineraryItem(itineraryItemId)
        }
    }
}