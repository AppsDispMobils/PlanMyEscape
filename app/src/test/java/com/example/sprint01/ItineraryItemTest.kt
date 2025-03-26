package com.example.sprint01

import com.example.sprint01.domain.model.ItineraryItem
import com.example.sprint01.domain.model.Trip
import com.example.sprint01.data.repository.TripRepositoryImpl
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ItineraryItemTest {

    private lateinit var itineraryTestRepository: TripRepositoryImpl

    @Before
    fun setUp() {
        itineraryTestRepository = TripRepositoryImpl()
        val testTrip = Trip(
            destination = "Madrid",
            startDate = "12-04-2025",
            endDate = "16-04-2025"
        )

        itineraryTestRepository.addTrip(testTrip)

    }

    @Test
    fun getTrips_correctList() {
        val testItineraryItem = ItineraryItem(
            title = "Gran Via",
            tripId = itineraryTestRepository.getTrips()[0].id,
            description = "Paseo",
            date = "14-04-2025"
        )

        val testItineraryItem2 = ItineraryItem(
            title = "Parque del Retiro",
            tripId = itineraryTestRepository.getTrips()[0].id,
            description = "Visitar",
            date = "16-04-2025"
        )

        itineraryTestRepository.addItineraryItem(testItineraryItem)
        itineraryTestRepository.addItineraryItem(testItineraryItem2)
        assertEquals(2, itineraryTestRepository.getItineraryItemsfromTrip(itineraryTestRepository.getTrips()[0].id).size)

        val itineraryItem = itineraryTestRepository.getItineraryItemsfromTrip(itineraryTestRepository.getTrips()[0].id)[0]
        assertEquals("Gran Via", itineraryItem.title)
        assertEquals("Paseo", itineraryItem.description)
        assertEquals("14-04-2025", itineraryItem.date)

        val itineraryItem2 = itineraryTestRepository.getItineraryItemsfromTrip(itineraryTestRepository.getTrips()[0].id)[1]
        assertEquals("Parque del Retiro", itineraryItem2.title)
        assertEquals("Visitar", itineraryItem2.description)
        assertEquals("16-04-2025", itineraryItem2.date)

    }

    @Test
    fun addItineraryItem_isCorrect() {
        val testItineraryItem = ItineraryItem(
            title = "Gran Via",
            tripId = itineraryTestRepository.getTrips()[0].id,
            description = "Paseo",
            date = "14-04-2025"
        )

        itineraryTestRepository.addItineraryItem(testItineraryItem)
        assertEquals(1, itineraryTestRepository.getItineraryItemsfromTrip(itineraryTestRepository.getTrips()[0].id).size)

        val itineraryItem = itineraryTestRepository.getItineraryItemsfromTrip(itineraryTestRepository.getTrips()[0].id)[0]

        assertEquals("Gran Via", itineraryItem.title)
        assertEquals("Paseo", itineraryItem.description)
        assertEquals("14-04-2025", itineraryItem.date)
    }

    @Test
    fun updateItineraryItem_isCorrect() {
        val testItineraryItem = ItineraryItem(
            title = "Gran Via",
            tripId = itineraryTestRepository.getTrips()[0].id,
            description = "Paseo",
            date = "14-04-2025"
        )

        itineraryTestRepository.addItineraryItem(testItineraryItem)
        var itineraryItem = itineraryTestRepository.getItineraryItemsfromTrip(itineraryTestRepository.getTrips()[0].id)[0]
        assertEquals("Gran Via", itineraryItem.title)
        assertEquals("Paseo", itineraryItem.description)
        assertEquals("14-04-2025", itineraryItem.date)

        val updatedItineraryItem = ItineraryItem(
            id = 1,
            title = "Parque del Retiro",
            tripId = itineraryTestRepository.getTrips()[0].id,
            description = "Visitar",
            date = "16-04-2025"
        )

        itineraryTestRepository.updateItineraryItem(updatedItineraryItem)
        itineraryItem = itineraryTestRepository.getItineraryItemsfromTrip(itineraryTestRepository.getTrips()[0].id)[0]
        assertEquals("Parque del Retiro", itineraryItem.title)
        assertEquals("Visitar", itineraryItem.description)
        assertEquals("16-04-2025", itineraryItem.date)

    }

    @Test
    fun deleteItineraryItem_isCorrect() {
        val testItineraryItem = ItineraryItem(
            title = "Gran Via",
            tripId = itineraryTestRepository.getTrips()[0].id,
            description = "Paseo",
            date = "14-04-2025"
        )

        itineraryTestRepository.addItineraryItem(testItineraryItem)
        assertEquals(1, itineraryTestRepository.getItineraryItemsfromTrip(itineraryTestRepository.getTrips()[0].id).size)

        val itineraryItem = itineraryTestRepository.getItineraryItemsfromTrip(itineraryTestRepository.getTrips()[0].id)[0]

        itineraryTestRepository.deleteItineraryItem(itineraryItem.id)
        assertEquals(0, itineraryTestRepository.getItineraryItemsfromTrip(itineraryTestRepository.getTrips()[0].id).size)
    }
}