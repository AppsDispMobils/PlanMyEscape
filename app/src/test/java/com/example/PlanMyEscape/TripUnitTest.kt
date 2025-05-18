/*package com.example.PlanMyEscape

import com.example.PlanMyEscape.domain.model.Trip
import com.example.PlanMyEscape.data.repository.TripRepositoryImpl
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class TripUnitTest {

    private lateinit var tripTestRepository: TripRepositoryImpl

    @Before
    fun setUp() {
        tripTestRepository = TripRepositoryImpl()
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

        tripTestRepository.addTrip(testTrip1)
        tripTestRepository.addTrip(testTrip2)

        assertEquals(2, tripTestRepository.getTrips().size)

        val firstTrip = tripTestRepository.getTrips()[0]
        assertEquals(1, firstTrip.id)
        assertEquals("Madrid", firstTrip.destination)
        assertEquals("12-04-2025", firstTrip.startDate)
        assertEquals("14-04-2025", firstTrip.endDate)

        val secondTrip = tripTestRepository.getTrips()[1]
        assertEquals(2, secondTrip.id)
        assertEquals("Barcelona", secondTrip.destination)
        assertEquals("20-06-2025", secondTrip.startDate)
        assertEquals("28-06-2025", secondTrip.endDate)
    }

    @Test
    fun addTrip_isCorrect() {
        val testTrip = Trip(
            destination = "Madrid",
            startDate = "12-04-2025",
            endDate = "14-04-2025"
        )
        tripTestRepository.addTrip(testTrip)

        val trip = tripTestRepository.getTrips()[0]

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

        tripTestRepository.addTrip(testTrip)

        var trip = tripTestRepository.getTrips()[0]

        assertEquals("Madrid", trip.destination)
        assertEquals("12-04-2025", trip.startDate)
        assertEquals("14-04-2025", trip.endDate)

        val updatedTrip = Trip(
            id = 1,
            destination = "Barcelona",
            startDate = "20-06-2025",
            endDate = "28-06-2025"
        )

        tripTestRepository.updateTrip(updatedTrip)

        trip = tripTestRepository.getTrips()[0]

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

        tripTestRepository.addTrip(testTrip)
        assertEquals(1, tripTestRepository.getTrips().size)

        val tripId = tripTestRepository.getTrips()[0].id

        tripTestRepository.deleteTrip(tripId)
        assertEquals(0, tripTestRepository.getTrips().size)
    }

}

 */