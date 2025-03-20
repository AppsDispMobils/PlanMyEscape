package com.example.sprint01.ui.view

import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sprint01.R
import com.example.sprint01.R.color.icon_color
import com.example.sprint01.domain.model.Trip
import com.example.sprint01.ui.screens.BottomNavigationBar
import com.example.sprint01.ui.viewmodel.ProgrammedTripsViewModel
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgrammedTripsScreen(
    navController: NavController,
    viewModel: ProgrammedTripsViewModel = hiltViewModel()
) {
    val trips = viewModel.trips
    val selectedIndex by remember { mutableStateOf(0) }

    var isEditingTrip by remember { mutableStateOf(false) }
    var showTripDialog by remember { mutableStateOf(false) }
    var currentTripId by remember { mutableStateOf(0) }
    var tripDestination by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }

    //Validacion datos de entrada
    var dateError by remember { mutableStateOf<String?>(null) }
    var fieldError by remember { mutableStateOf<String?>(null) }





    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Viajes Programados") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Volver a viajes"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    isEditingTrip = false
                    tripDestination = ""
                    startDate = ""
                    endDate = ""
                    showTripDialog = true

                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Añadir Viaje"
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(
                selectedIndex = selectedIndex,
                navController = navController
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            LazyColumn {
                items(trips) { trip ->
                    TripCard(
                        trip = trip,
                        onOpen = {
                        navController.navigate("tripDetails/${trip.Id}")
                    },
                        onEdit = {
                            isEditingTrip = true
                            currentTripId = trip.Id
                            tripDestination = trip.destination
                            startDate = trip.startDate
                            endDate = trip.endDate
                            showTripDialog = true
                        },
                        onDelete = {
                            viewModel.deleteTrip(trip.Id)
                        }
                    )
                }
            }


        }
    }


    if(showTripDialog) {
        AlertDialog(
            onDismissRequest = { showTripDialog = false },
            title = { Text(text = if (isEditingTrip) stringResource(id = R.string.updt_trip) else stringResource(id = R.string.add_trip)) },
            text = {
                Column {
                    OutlinedTextField(
                        value = tripDestination,
                        onValueChange = { tripDestination = it },
                        label = { Text(stringResource(id = R.string.trip_Card1)) },
                        modifier = Modifier.fillMaxWidth(),
                        isError = fieldError != null,
                        supportingText = {
                            if (fieldError != null) {
                                Text(text = fieldError!!, color = Color.Red)
                            }
                        }
                    )
                    OutlinedTextField(
                        value = startDate,
                        onValueChange = { startDate = it },
                        label = { Text(stringResource(id = R.string.trip_Card2) + " (dd-MM-yyyy)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        isError = dateError != null,
                        supportingText = {
                            if(dateError != null) {
                                Text(text = dateError!!, color = Color.Red)
                            }
                        }
                    )
                    OutlinedTextField(
                        value = endDate,
                        onValueChange = { endDate = it },
                        label = { Text(stringResource(id = R.string.trip_Card3) + " (dd-MM-yyyy)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        isError = dateError != null,
                        supportingText = {
                            if(dateError != null) {
                                Text(text = dateError!!, color = Color.Red)
                            }
                        }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        dateError = if (areValidDates(startDate, endDate)) null else "Fechas no válidas"
                        fieldError = if (validateFields(tripDestination, startDate, endDate)) null else "Faltan campos requeridos"

                        if ((dateError == null) && (fieldError == null)){
                            if (isEditingTrip) {
                                viewModel.updateTrip(
                                    Trip(
                                        Id = currentTripId,
                                        destination = tripDestination,
                                        startDate = startDate,
                                        endDate = endDate
                                    )
                                )
                            } else {
                                viewModel.addTrip(
                                    Trip(
                                        destination = tripDestination,
                                        startDate = startDate,
                                        endDate = endDate
                                    )
                                )
                            }
                            showTripDialog = false
                        }
                    }
                ) {
                    Text(stringResource(id = R.string.boton_addTrip1))
                }
            },
            dismissButton = {
                Button(onClick = { showTripDialog = false }) {
                    Text(stringResource(id = R.string.boton_addTrip2))
                }
            }
        )
    }

}

fun validateFields(firstInput: String, secondInput: String, thirdInput: String): Boolean {
    var isValid = true

    if (firstInput.isBlank()) { isValid = false }

    if (secondInput.isBlank()) { isValid = false }

    if (thirdInput.isBlank()) { isValid = false }

    return isValid
}

fun areValidDates(startDate: String, endDate: String): Boolean {
    if (!isValidDate(startDate) || !isValidDate(endDate) ) return false

    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    dateFormat.isLenient = false
    val start = dateFormat.parse(startDate)
    val end = dateFormat.parse(endDate)
    if (start != null) {
        return start.before(end)
    }
    return false
}

fun isValidDate(date: String): Boolean {
    return try {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        dateFormat.isLenient = false // Evita que fechas como "30-02-2023" sean válidas
        val parsedDate = dateFormat.parse(date)

        // Verificar si la fecha es pasada
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time


            if (parsedDate.before(today)) {
                return false // La fecha es pasada
            }

        true
    } catch (e: java.text.ParseException) {
        false
    }
}


@Composable
fun TripCard(
    trip: Trip,
    onOpen: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(color = colorResource(icon_color), shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
            .clickable { onOpen() }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = stringResource(id = R.string.trip_Card1) + ": ${trip.destination}", style = MaterialTheme.typography.titleMedium)
            Text(text = stringResource(id = R.string.trip_Card2) + ": ${trip.startDate}", style = MaterialTheme.typography.bodyMedium)
            Text(text = stringResource(id = R.string.trip_Card3) + ": ${trip.endDate}", style = MaterialTheme.typography.bodyMedium)
            Row {
                IconButton(onEdit) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Editar Viaje"
                    )
                }
                IconButton(onDelete) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Eliminar Viaje"
                    )
                }
            }

        }
    }
}


