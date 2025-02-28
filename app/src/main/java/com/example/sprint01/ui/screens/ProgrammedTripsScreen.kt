package com.example.sprint01.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sprint01.R.color.icon_color
import com.example.sprint01.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgrammedTripsScreen(navController: NavController) {

    val selectedIndex by remember { mutableStateOf(0) }

    val tripsList = listOf(
        Trip(
            tripId = "1",
            userId = "user123",
            startDate = "10/03/2025",  // Fecha de inicio
            endDate = "12/03/2025",    // Fecha de fin
            destination = "Paris"
        ),
        Trip(
            tripId = "2",
            userId = "user123",
            startDate = "3/04/2025",   // Fecha de inicio
            endDate = "7/04/2025",     // Fecha de fin
            destination = "London"
        ),
        Trip(
            tripId = "3",
            userId = "user124",
            startDate = "28/04/2025",   // Fecha de inicio
            endDate = "4/05/2025",     // Fecha de fin
            destination = "New York"
        ),
        Trip(
            tripId = "4",
            userId = "user123",
            startDate = "7/10/2025",  // Fecha de inicio
            endDate = "12/10/2025",  // Fecha de fin
            destination = "Tokyo"
        )
    )


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Viajes Programados") },

            )
        },

        bottomBar = {
            BottomNavigationBar(
                selectedIndex = selectedIndex,
                navController = navController
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                tripsList.forEach { trip ->
                    TripCard(trip, navController)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    )
}

@Composable
fun TripCard(trip: Trip, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(color = colorResource(icon_color), shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
            .clickable { navController.navigate("tripDetails") }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Destino: ${trip.destination}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Fecha de inicio: ${trip.startDate}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Fecha de fin: ${trip.endDate}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}



data class Trip(
    val tripId: String,
    val userId: String,
    val startDate: String,
    val endDate: String,
    val destination: String
)