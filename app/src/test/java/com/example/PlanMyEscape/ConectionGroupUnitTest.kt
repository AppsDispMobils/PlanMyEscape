package com.example.PlanMyEscape



import com.example.PlanMyEscape.data.remote.api.HotelApiService
import com.example.PlanMyEscape.data.remote.dto.AvailabilityResponseDto
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConectionGroupUnitTest {

    private val api: HotelApiService by lazy {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        Retrofit.Builder()
            .baseUrl("http://13.39.162.212/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HotelApiService::class.java)
    }

    @Test
    fun testGetAvailability_returnsValidResponse() = runBlocking {
        val response: AvailabilityResponseDto = api.getAvailability(
            groupId = "G03",
            startDate = "2025-06-01",
            endDate = "2025-06-10"
        )


        assertNotNull("La respuesta no debe ser nula", response)


        val hotelWithIdBCN01 = response.available_hotels.any { it.id == "BCN01" }

        assertTrue("El hotel con id BCN01 debería estar disponible", hotelWithIdBCN01)

    }
}


