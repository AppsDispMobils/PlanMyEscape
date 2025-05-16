package com.example.PlanMyEscape.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.PlanMyEscape.ui.viewmodel.ProgrammedTripsViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.State
import coil.compose.rememberAsyncImagePainter
import com.example.PlanMyEscape.BuildConfig
import com.example.PlanMyEscape.R
import com.example.PlanMyEscape.domain.model.Room
import com.example.PlanMyEscape.ui.viewmodel.HotelDetailsUiState
import com.example.PlanMyEscape.ui.viewmodel.HotelDetailsViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit



@Composable
fun HotelDetailsScreen(
    hotelId: String,
    startDate: String,
    endDate: String,
    navController: NavController,
    hotelViewModel: HotelDetailsViewModel = hiltViewModel(),
    tripViewModel: ProgrammedTripsViewModel = hiltViewModel()
) {

    val ui = hotelViewModel.uiState.collectAsState()
    val base = BuildConfig.HOTELS_API_URL.trimEnd('/')
    val groupId = BuildConfig.GROUP_ID

    val selectedIndex by remember { mutableStateOf(0) }
    val dateFormat = DateTimeFormatter.ISO_DATE
    val nights = ChronoUnit.DAYS.between(LocalDate.parse(startDate, dateFormat), LocalDate.parse(endDate, dateFormat)).toInt()


    LaunchedEffect(hotelId) {
        hotelViewModel.load(hotelId, groupId, startDate, endDate)
    }

    Scaffold(
        topBar = {
            TopNavigationBar(
                navController = navController,
                title = ui.value.hotel?.name ?: ""
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedIndex = selectedIndex,
                navController = navController
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hotel Header
            item {
                Column(Modifier.padding(16.dp)) {
                    HotelHeader(
                        imageUrl = ui.value.hotel?.imageUrl,
                        name = ui.value.hotel?.name,
                        address = ui.value.hotel?.address,
                        rating = ui.value.hotel?.rating,
                        baseUrl = base,
                        nights = nights,
                        dateRange = "$startDate to $endDate"
                    )
                }
            }

            // Rooms List
            items(ui.value.rooms ?: emptyList()) { room ->
                RoomCard(
                    room = room,
                    nights = nights,
                    baseUrl = base,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    hotelViewModel = hotelViewModel,
                    ui = ui,
                    navController = navController
                )
            }
        }
    }

}

@Composable
private fun HotelHeader(
    imageUrl: String?,
    name: String?,
    address: String?,
    rating: Int?,
    baseUrl: String,
    nights: Int,
    dateRange: String
) {
    Column {
        imageUrl?.let { url ->
            Image(
                painter = rememberAsyncImagePainter(baseUrl + url),
                contentDescription = "Hotel image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(Modifier.height(16.dp))
        }

        Text(
            text = name ?: "",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = address ?: "",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            rating?.let {
                RatingBar(
                    rating = it,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("•", modifier = Modifier.padding(horizontal = 4.dp))
            }
            Text("$nights nights • $dateRange")
        }
    }
}

@Composable
private fun RoomCard(
    room: Room,
    nights: Int,
    baseUrl: String,
    modifier: Modifier = Modifier,
    hotelViewModel: HotelDetailsViewModel,
    ui: State<HotelDetailsUiState>,
    navController: NavController
) {
    var showConfirmation by remember { mutableStateOf(false) }
    var showRoomImage by remember { mutableStateOf(false) }
    var imageToShow by remember { mutableStateOf<List<String>?>(null) }

    val selectedRoom = ui.value.selectedRoom
    val totalPrice = room.price * nights

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.app_color)
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            // Room Type and Price
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = room.roomType.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Room ${room.id}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = "${room.price}€ / night",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(Modifier.height(12.dp))

            // Room Image
            room.images.firstOrNull()?.let { image ->
                Image(
                    painter = rememberAsyncImagePainter(baseUrl + image),
                    contentDescription = "Room ${room.id} image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            Spacer(Modifier.height(16.dp))

            // Total Price
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Total for $nights nights")
                Text(
                    text = "$totalPrice€",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                Button(onClick = {
                    hotelViewModel.selectRoom(room)
                    showConfirmation = true
                }) {
                    Text(text = "Reservar")
                }
            }
        }

        if (showConfirmation && selectedRoom != null) {
            val total = selectedRoom.price * nights
            AlertDialog(
                onDismissRequest = { showConfirmation = false },
                confirmButton = {
                    TextButton(onClick = {
                        showConfirmation = false
                        hotelViewModel.reserveRoom(selectedRoom)
                        navController.popBackStack()
                    }) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showConfirmation = false }) {
                        Text("Cancel")
                    }
                },
                title = { Text("Confirm Reservation") },
                text = {
                    Column {
                        Text("Hotel: ${ui.value.hotel?.name} (${ui.value.hotel?.id})")
                        Text("Room: ${selectedRoom.roomType} (${selectedRoom.id})")
                        Text("Price: €${selectedRoom.price} x $nights nights")
                        Text("Total: €$total")
                    }
                }
            )
        }
    }
}
