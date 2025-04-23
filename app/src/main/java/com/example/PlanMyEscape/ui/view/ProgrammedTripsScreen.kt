package com.example.PlanMyEscape.ui.view

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
import android.app.DatePickerDialog
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.PlanMyEscape.R
import com.example.PlanMyEscape.R.color.app_color
import com.example.PlanMyEscape.domain.model.Trip
import com.example.PlanMyEscape.ui.viewmodel.ProgrammedTripsViewModel
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
    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    var isEditingTrip by remember { mutableStateOf(false) }
    var showTripDialog by remember { mutableStateOf(false) }
    var currentTripId by remember { mutableStateOf(0) }
    var currentUserId by remember { mutableStateOf("")}
    var tripDestination by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }

    //Validacion datos de entrada
    var dateError by remember { mutableStateOf<String?>(null) }
    var fieldError by remember { mutableStateOf<String?>(null) }
    var destinationError by remember { mutableStateOf<String?>(null) }





    Scaffold(
        topBar = {
            TopNavigationBar(
                navController = navController,
                title = stringResource(id = R.string.ProgrammedTrips)
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
                            navController.navigate("tripDetails/${trip.id}/${trip.startDate}/${trip.endDate}")
                        },
                        onEdit = {
                            isEditingTrip = true
                            currentTripId = trip.id
                            tripDestination = trip.destination
                            startDate = trip.startDate
                            endDate = trip.endDate
                            currentUserId = trip.userId
                            showTripDialog = true
                        },
                        onDelete = {
                            viewModel.deleteTrip(trip.id)
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
                        isError = fieldError != null && destinationError != null,
                        supportingText = {
                            if (fieldError != null) {
                                Text(text = fieldError!!, color = Color.Red)
                            } else if (destinationError != null) {
                                Text(text = destinationError!!, color = Color.Red)
                            }
                        }
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                DatePickerDialog(
                                    context,
                                    { _, year, month, dayOfMonth ->
                                        startDate = "%04d-%02d-%02d".format(year, month + 1, dayOfMonth)
                                    },
                                    calendar.get(Calendar.YEAR),
                                    calendar.get(Calendar.MONTH),
                                    calendar.get(Calendar.DAY_OF_MONTH)
                                ).show()
                            }
                    ){
                        OutlinedTextField(
                            value = startDate,
                            onValueChange = {  },
                            label = { Text(stringResource(id = R.string.trip_Card2)) },
                            readOnly = true,
                            enabled = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            isError = dateError != null,
                            supportingText = {
                                if (dateError != null) {
                                    Text(text = dateError!!, color = Color.Red)
                                }
                            }
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                DatePickerDialog(
                                    context,
                                    { _, year, month, dayOfMonth ->
                                        endDate = "%04d-%02d-%02d".format(year, month + 1, dayOfMonth)
                                    },
                                    calendar.get(Calendar.YEAR),
                                    calendar.get(Calendar.MONTH),
                                    calendar.get(Calendar.DAY_OF_MONTH)
                                ).show()
                            }
                    ) {
                        OutlinedTextField(
                            value = endDate,
                            onValueChange = { endDate = it },
                            label = { Text(stringResource(id = R.string.trip_Card3)) },
                            readOnly = true,
                            enabled = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            isError = dateError != null,
                            supportingText = {
                                if (dateError != null) {
                                    Text(text = dateError!!, color = Color.Red)
                                }
                            }
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        dateError =
                            if (areValidDates(startDate, endDate)) null else "Fechas no válidas"
                        fieldError = if (validateFields(
                                tripDestination,
                                startDate,
                                endDate
                            )
                        ) null else "Faltan campos requeridos"
                        viewModel.validateTripDestination(tripDestination.uppercase()) { isAvailable ->
                            destinationError =
                                if (isAvailable) null else "El destino ya pertenece a un viaje"


                            if ((dateError == null) && (fieldError == null) && (destinationError == null)) {
                                if (isEditingTrip) {
                                    viewModel.updateTrip(
                                        Trip(
                                            id = currentTripId,
                                            destination = tripDestination.uppercase(),
                                            startDate = startDate,
                                            endDate = endDate,
                                            userId = currentUserId
                                        )
                                    )
                                } else {
                                    viewModel.addTrip(
                                        Trip(
                                            destination = tripDestination.uppercase(),
                                            startDate = startDate,
                                            endDate = endDate,
                                            userId = currentUserId
                                        )
                                    )
                                }
                                showTripDialog = false
                            }
                        }
                    }
                ) {
                    Text(stringResource(id = R.string.botonGuardar))
                }
            },
            dismissButton = {
                Button(onClick = { showTripDialog = false }) {
                    Text(stringResource(id = R.string.botonCancelar))
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

    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
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
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(
    navController: NavController,
    title: String
) {
    val appColor = colorResource(id = R.color.app_color)
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    Icons.Default.ArrowBack,
                    tint = Color.Gray,
                    contentDescription = "Volver a viajes"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = appColor,
            titleContentColor = Color.White // Color del texto del título
        )
    )
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
            .background(color = colorResource(app_color), shape = RoundedCornerShape(8.dp))
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


