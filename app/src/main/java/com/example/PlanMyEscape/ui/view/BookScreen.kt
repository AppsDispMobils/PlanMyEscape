package com.example.PlanMyEscape.ui.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.PlanMyEscape.BuildConfig
import com.example.PlanMyEscape.R
import com.example.PlanMyEscape.domain.model.Hotel
import com.example.PlanMyEscape.ui.viewmodel.BookViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.ceil
import kotlin.math.floor

val base = BuildConfig.HOTELS_API_URL.trimEnd('/')

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookScreen(
    navController: NavController,
    viewModel: BookViewModel = hiltViewModel()
)
{
    val selectedIndex by remember { mutableStateOf(0) }
    val ui by viewModel.uiState.collectAsState()
    val appColor = colorResource(id = R.color.app_color)


    Scaffold (
        topBar = {
            TopNavigationBar(
                navController = navController,
                title = "Buscar destinos"
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedIndex = selectedIndex,
                navController = navController
            )
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ){

            ExposedDropdownMenuBox(
                expanded = ui.cityMenu,
                onExpandedChange = { viewModel.toggleCityMenu()}

            ) {
                OutlinedTextField(
                    value = ui.city,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("City") },
                    leadingIcon = { Icon(Icons.Default.Place, null, tint = appColor) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = appColor,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = appColor,
                        focusedTextColor = Color.DarkGray
                    ),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = ui.cityMenu,
                    onDismissRequest = { viewModel.toggleCityMenu() }
                ) {
                    listOf("Barcelona", "Paris", "Londres").forEach { city ->
                        DropdownMenuItem(
                            text = { Text(city) },
                            onClick = { viewModel.selectCity(city) }
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
            DateField("Start date", ui.startDate) { viewModel.pickStart(it) }
            Spacer(Modifier.height(8.dp))
            DateField("End date", ui.endDate) { viewModel.pickEnd(it) }

            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { viewModel.search() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.app_color),
                    contentColor = Color.DarkGray
                )
            ) {
                Text("Buscar")
            }

            Spacer(Modifier.height(16.dp))

            if(ui.loading) {
                CircularProgressIndicator()
            } else {
                HotelList(ui.hotels) { hotel ->
                    navController.navigate("hotelDetails/${hotel.id}/${ui.startDate}/${ui.endDate}" )
                }

                if (ui.message != null) {
                    Text(
                        text = ui.message!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }

        }
    }
}

@Composable
fun DateField(
    label: String,
    date: LocalDate?,
    onPick: (LocalDate) -> Unit
) {
    val context = LocalContext.current
    val formatter = DateTimeFormatter.ISO_DATE

    OutlinedTextField(
        value = date?.format(formatter) ?: "",
        onValueChange = {},
        readOnly = true,
        enabled = false,                     // ← evita que consuma el click
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val now = date ?: LocalDate.now()
                android.app.DatePickerDialog(
                    context,
                    { _, y, m, d ->
                        onPick(LocalDate.of(y, m + 1, d))   // meses 0-based
                    },
                    now.year, now.monthValue - 1, now.dayOfMonth
                ).show()
            }
    )
}

@Composable
fun HotelList(
    hotels: List<Hotel>,
    onClick: (Hotel) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(hotels) { hotel ->
            HotelCard(
                hotel = hotel,
                onClick = { onClick(hotel) },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun HotelCard(
    hotel: Hotel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val minPrice = hotel.rooms?.minOfOrNull { it.price } ?: 0.0

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.app_color)
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.height(140.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Hotel Image
            hotel.imageUrl?.let { imageUrl ->
                Image(
                    painter = rememberAsyncImagePainter(base + imageUrl),
                    contentDescription = "Hotel ${hotel.name} image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(140.dp)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
                )
            }

            // Hotel Info
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = hotel.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "(${hotel.id})",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = hotel.address,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(8.dp))

                // Rating and Price
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RatingBar(
                        rating = hotel.rating,
                        modifier = Modifier.width(100.dp)
                    )

                    Text(
                        text = "From ${minPrice}€",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun RatingBar(
    rating: Int,
    modifier: Modifier = Modifier,
    stars: Int = 5,
    starsColor: Color = Color.Gray
) {
    Row(modifier = modifier) {
        // Filled stars
        repeat(rating.coerceAtMost(stars)) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Filled star",
                tint = starsColor,
                modifier = Modifier.size(16.dp)
            )
        }

        // Empty stars
        repeat(stars - rating) {
            Icon(
                imageVector = Icons.Default.StarOutline,
                contentDescription = "Empty star",
                tint = starsColor,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
