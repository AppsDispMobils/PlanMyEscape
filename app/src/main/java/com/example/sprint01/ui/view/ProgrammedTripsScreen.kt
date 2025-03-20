package com.example.sprint01.ui.view

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sprint01.R.color.icon_color
import com.example.sprint01.domain.model.Trip
import com.example.sprint01.ui.screens.BottomNavigationBar
import com.example.sprint01.ui.viewmodel.ProgrammedTripsViewModel


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
            title = { Text(text = if (isEditingTrip) "Editar Viaje" else "Añadir Viaje") },
            text = {
                Column {
                    OutlinedTextField(
                        value = tripDestination,
                        onValueChange = { tripDestination = it },
                        label = { Text("Destino") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = startDate,
                        onValueChange = { startDate = it },
                        label = { Text("Fecha de inicio") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                    OutlinedTextField(
                        value = endDate,
                        onValueChange = { endDate = it },
                        label = { Text("Fecha de final") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if(isEditingTrip) {
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
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                Button(onClick = { showTripDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
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
            Text(text = "Destino: ${trip.destination}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Fecha de inicio: ${trip.startDate}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Fecha de fin: ${trip.endDate}", style = MaterialTheme.typography.bodyMedium)
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


