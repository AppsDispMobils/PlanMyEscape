package com.example.sprint01

import com.example.sprint01.domain.model.Trip
import com.example.sprint01.domain.repository.TripRepositoryImpl
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class TripUnitTest {

    private lateinit var tripRepository: TripRepositoryImpl

    @Before
    fun setUp() {
        tripRepository = TripRepositoryImpl()
    }

    @Test
    fun addTrip_isCorrect() {
        val testTrip = Trip(
            destination = "Madrid",
            startDate = "12-04-2025",
            endDate = "14-04-2025"
        )
        tripRepository.addTrip(testTrip)

        val trip = tripRepository.getTrips()[0]

        assertEquals("Madrid", trip.destination)
        assertEquals("12-04-2025", trip.startDate)
        assertEquals("14-04-2025", trip.endDate)
    }

    @Test
    fun editTrip_isCorrect() {
        val testTrip = Trip(
            destination = "Madrid",
            startDate = "12-04-2025",
            endDate = "14-04-2025"
        )

        tripRepository.addTrip(testTrip)

        var trip = tripRepository.getTrips()[0]

        assertEquals("Madrid", trip.destination)
        assertEquals("12-04-2025", trip.startDate)
        assertEquals("14-04-2025", trip.endDate)

        val updatedTrip = Trip(
            Id = 1,
            destination = "Barcelona",
            startDate = "20-06-2025",
            endDate = "28-06-2025"
        )

        tripRepository.updateTrip(updatedTrip)

        trip = tripRepository.getTrips()[0]

        assertEquals("Barcelona", trip.destination)
        assertEquals("20-06-2025", trip.startDate)
        assertEquals("28-06-2025", trip.endDate)

    }

    @Test
    fun deleteTrip_isCorrect() {
        val testTrip = Trip(
            destination = "Madrid",
            startDate = "12-04-2025",
            endDate = "14-04-2025"
        )

        tripRepository.addTrip(testTrip)
        assertEquals(1, tripRepository.getTrips().size)

        val tripId = tripRepository.getTrips()[0].Id

        tripRepository.deleteTrip(tripId)
        assertEquals(0, tripRepository.getTrips().size)
    }

    @Test
    fun getTrips_correctList() {
        val testTrip1 = Trip(
            destination = "Madrid",
            startDate = "12-04-2025",
            endDate = "14-04-2025"
        )

        val testTrip2 = Trip(
            destination = "Barcelona",
            startDate = "20-06-2025",
            endDate = "28-06-2025"
        )

        tripRepository.addTrip(testTrip1)
        tripRepository.addTrip(testTrip2)

        assertEquals(2, tripRepository.getTrips().size)

        val firstTrip = tripRepository.getTrips()[0]
        assertEquals(1, firstTrip.Id)
        assertEquals("Madrid", firstTrip.destination)
        assertEquals("12-04-2025", firstTrip.startDate)
        assertEquals("14-04-2025", firstTrip.endDate)

        val secondTrip = tripRepository.getTrips()[1]
        assertEquals(2, secondTrip.Id)
        assertEquals("Barcelona", secondTrip.destination)
        assertEquals("20-06-2025", secondTrip.startDate)
        assertEquals("28-06-2025", secondTrip.endDate)
    }
}