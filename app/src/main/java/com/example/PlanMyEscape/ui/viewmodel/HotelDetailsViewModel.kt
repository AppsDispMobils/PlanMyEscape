package com.example.PlanMyEscape.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.PlanMyEscape.domain.model.Hotel
import com.example.PlanMyEscape.domain.model.Room
import com.example.PlanMyEscape.domain.repository.HotelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotelDetailsViewModel @Inject constructor(
    private val hotelRepository: HotelRepository
): ViewModel()
{

    private val _uiState = MutableStateFlow(HotelDetailsUiState())
    val uiState: StateFlow<HotelDetailsUiState> = _uiState

    fun selectRoom(room: Room) {
        _uiState.value = _uiState.value.copy(selectedRoom = room)
    }

    private lateinit var groupId: String
    private lateinit var start: String
    private lateinit var end: String

    fun load(hotelId: String, gid: String, s: String, e: String) {
        if(uiState.value.hotel != null) return
        groupId = gid
        start = s
        end = e
        viewModelScope.launch {
            val hotel = hotelRepository.getHotels(gid).first() { it.id == hotelId}
            val freeRooms = hotelRepository.getAvailability(gid, s, e).first() { it.id == hotelId}.rooms
            _uiState.value = HotelDetailsUiState(false, hotel, freeRooms)
        }
    }

    fun reserveRoom(room: Room) {
        /*TODO*/
    }


}

data class HotelDetailsUiState (
    val loading: Boolean = true,
    val hotel: Hotel? = null,
    val rooms: List<Room>? = null,
    val selectedRoom: Room? = null,
    val showImageDialog: Boolean = false
)
