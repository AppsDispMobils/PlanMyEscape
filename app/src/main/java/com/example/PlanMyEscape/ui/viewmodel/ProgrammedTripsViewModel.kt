package com.example.PlanMyEscape.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.PlanMyEscape.domain.model.Trip
import com.example.PlanMyEscape.domain.repository.AuthenticationRepository
import com.example.PlanMyEscape.domain.repository.TripRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgrammedTripsViewModel @Inject constructor(
    private val tripRepository: TripRepository,
    private val authRepository : AuthenticationRepository
) : ViewModel() {

    private val _trips = mutableStateListOf<Trip>()
    val trips = _trips

    init {
        loadTrips()
    }

    private fun loadTrips() {
        trips.clear()
        viewModelScope.launch {
            trips.addAll(tripRepository.getTrips())
            Log.d("Trip", "Showing all trips")
        }
    }

    fun addTrip(trip: Trip) {
        viewModelScope.launch {
            val userId = authRepository.getCurrentId()
            tripRepository.addTrip(trip.copy(userId = userId))
            Log.d("Trip", "Added new trip: ${trip.destination}")
            loadTrips()
        }
    }

    fun deleteTrip(tripId: Int) {
        viewModelScope.launch {
            tripRepository.deleteTrip(tripId)
            Log.d("Trip", "Deleting trip...")
            loadTrips()
        }
    }

    fun updateTrip(trip: Trip) {
        viewModelScope.launch {
            tripRepository.updateTrip(trip)
            Log.d("Trip", "Updated ${trip.destination}")
            loadTrips()
        }
    }

    fun validateTripDestination(tripDestination: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val count = tripRepository.validateTripDestination(tripDestination)
            Log.d("Trip", "Trip con el mismo destino: " + count )
            onResult(count == 0) // Si count es 0, el destino está disponible
        }
    }


}