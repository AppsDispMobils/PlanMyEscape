package com.example.PlanMyEscape.ui.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.PlanMyEscape.data.repository.GalleryRepository
import com.example.PlanMyEscape.data.local.PlanMyEscapeDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GalleryViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = GalleryRepository(
        PlanMyEscapeDatabase.get(app).tripDao()
    )



    val trips = repo.trips
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),
            emptyList())


    fun addImage(tripId: Int, uri: Uri) = viewModelScope.launch {
        repo.addImage(tripId, uri)
    }

    fun deleteImage(uri: Uri) = viewModelScope.launch {
        repo.deleteImageByUri(uri)
    }
}
