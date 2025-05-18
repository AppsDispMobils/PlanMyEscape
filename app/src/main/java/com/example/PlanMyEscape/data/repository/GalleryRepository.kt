package com.example.PlanMyEscape.data.repository

import android.net.Uri
import com.example.PlanMyEscape.data.TripWithImages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.PlanMyEscape.data.local.dao.TripDao
import com.example.PlanMyEscape.data.local.entity.ImageEntity
import java.io.File

import com.example.PlanMyEscape.domain.model.Trip


class GalleryRepository(private val dao: TripDao) {

    /** Expone los datos ya convertidos a modelo de UI */
    val trips: Flow<List<Trip>> = dao.getTripsWithImages()
        .map { list -> list.map { it.toTrip() } }


    suspend fun addImage(tripId: Int, uri: Uri) {
        dao.insertImage(ImageEntity(tripId = tripId, uri = uri.toString()))
    }

    /* ---------- mapping helpers ---------- */

    fun TripWithImages.toTrip(): Trip = Trip(
        id = trip.id,
        destination = trip.destination,
        startDate = trip.startDate,
        endDate = trip.endDate,
        userId = trip.userId,
        itineraryItems = emptyList(),
        images = images.map { Uri.parse(it.uri) }.toMutableList()
    )


    suspend fun deleteImageByUri(uri: Uri) {
        dao.deleteImageByUri(uri.toString())      // Room
        File(uri.path!!).delete()
    } // physical file

}