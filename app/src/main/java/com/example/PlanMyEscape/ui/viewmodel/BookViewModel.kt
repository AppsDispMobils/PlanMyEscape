package com.example.PlanMyEscape.ui.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.MutableState
import com.example.PlanMyEscape.BuildConfig
import com.example.PlanMyEscape.domain.model.Hotel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.PlanMyEscape.domain.repository.HotelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import retrofit2.HttpException

@HiltViewModel
class BookViewModel @Inject constructor(
    private val hotelRepository: HotelRepository
): ViewModel() {
    val groupId = BuildConfig.GROUP_ID


    private val _uiState = MutableStateFlow(BookUiState())
    val uiState: StateFlow<BookUiState> = _uiState

    //Añandir la busqueda de hoteles en BookScreen y que salgan los hoteles disponibles acorde con la busqueda

    fun toggleCityMenu() = _uiState.update { it.copy(cityMenu = !it.cityMenu) }
    fun selectCity(c: String) = _uiState.update { it.copy(city = c, cityMenu = false) }

    /* ---------- date pickers ---------- */
    fun pickStart(d: java.time.LocalDate) = _uiState.update { it.copy(startDate = d) }
    fun pickEnd(d: java.time.LocalDate)   = _uiState.update { it.copy(endDate = d) }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun search() = viewModelScope.launch {
        val s = _uiState.value.startDate ?: return@launch
        val e = _uiState.value.endDate   ?: return@launch
        val fmt = DateTimeFormatter.ISO_DATE
        val city = _uiState.value.city

        _uiState.update { it.copy(loading = true, message = null) }

        try {
            val hotels = hotelRepository.getAvailability(groupId, s.format(fmt), e.format(fmt), city = city)
            _uiState.update { it.copy(loading = false, hotels = hotels) }
            Log.d("Hotels", "View Model: ${_uiState.value.hotels.toString()}" )
        } catch (e: HttpException) {

            val decodedError = extractErrorMessage(e)

            Log.e("BookViewModel", "HTTP error: ${decodedError}  $e")
            _uiState.update { it.copy(loading = false, hotels = emptyList(), message = decodedError) }

            _uiState.update {
                it.copy(
                    loading = false,
                    hotels = emptyList(),
                    message = "Error: ${decodedError}}"
                )
            }

        } catch (e: Exception) {
            Log.e("BookViewModel", "Error: ${e.localizedMessage}")
//            _uiState.update { it.copy(loading = false, hotels = emptyList()) }
            _uiState.update {
                it.copy(
                    loading = false,
                    hotels = emptyList(),
                    message = "Error: ${e.message}}"
                )
            }
        }
    }
}

data class BookUiState(
    val loading: Boolean = false,
    val cityMenu: Boolean = false,
    val city: String = "Barcelona",
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val hotels: List<Hotel> = emptyList(),
    val message: String? = null
)

fun extractErrorMessage(e: HttpException): String {
    return try {
        val errorBody = e.response()?.errorBody()?.string()
        val json = JSONObject(errorBody ?: "")
        json.optString("detail", "Unknown error")
    } catch (ex: Exception) {
        "Unknown error"
    }
}