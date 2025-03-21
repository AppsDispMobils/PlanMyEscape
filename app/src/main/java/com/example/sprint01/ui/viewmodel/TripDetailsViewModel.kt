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
        Log.d("Itinerary","Cargando elementos de la lista: $itineraryItems")
        itineraryItems.clear()
        Log.d("Itinerary","Limpiar elementos de la lista: $itineraryItems")
        itineraryItems.addAll(repository.getItineraryItemsfromTrip(tripId))
        Log.d("Itinerary", "Mostrado en pantalla: $itineraryItems")
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

            repository.deleteItineraryItem(itineraryItemId)
            loadItineraryItems()

    }
}