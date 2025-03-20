package com.example.sprint01.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Scaffold
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sprint01.R.color.icon_color
import com.example.sprint01.domain.model.ItineraryItem
import com.example.sprint01.ui.screens.BottomNavigationBar
import com.example.sprint01.ui.viewmodel.TripDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetailsScreen(
    navController: NavController,
    viewModel: TripDetailsViewModel = hiltViewModel()
) {

    val itineraryItems = viewModel.itineraryItems

    val selectedIndex by remember { mutableStateOf(0) }

    var isEditingItineraryItem by remember { mutableStateOf(false) }
    var showItineraryItemDialog by remember { mutableStateOf(false) }
    var currentItineraryItemId by remember { mutableStateOf(0) }
    var itineraryItemTitle by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Trip Details") },
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
                    isEditingItineraryItem = false
                    itineraryItemTitle = ""
                    description = ""
                    date = ""
                    showItineraryItemDialog = true

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
                navController
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
                    items(itineraryItems) { itineraryItem ->
                        ItineraryItemCard(
                            itineraryItem = itineraryItem,
                            onEdit = {
                                isEditingItineraryItem = true
                                currentItineraryItemId = itineraryItem.Id
                                itineraryItemTitle = itineraryItem.title
                                description = itineraryItem.description
                                date = itineraryItem.date
                                showItineraryItemDialog = true
                            },
                            onDelete = {
                                viewModel.deleteItineraryItem(itineraryItem.Id)
                            }
                        )
                    }
                }


            }
        }

    if(showItineraryItemDialog) {
        AlertDialog(
            onDismissRequest = { showItineraryItemDialog = false },
            title = { Text(text = if (isEditingItineraryItem) "Editar Itinerario" else "Añadir Itinerario") },
            text = {
                Column {
                    OutlinedTextField(
                        value = itineraryItemTitle,
                        onValueChange = { itineraryItemTitle = it },
                        label = { Text("Titulo") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Descripción") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                    OutlinedTextField(
                        value = date,
                        onValueChange = { date = it },
                        label = { Text("Fecha") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (isEditingItineraryItem) {
                            viewModel.updateItineraryItem(
                                ItineraryItem(
                                    Id = currentItineraryItemId,
                                    title = itineraryItemTitle,
                                    description = description,
                                    tripId = viewModel.tripId,
                                    date = date
                                )
                            )
                        } else {
                            viewModel.addItitneraryItem(
                                ItineraryItem(
                                    title = itineraryItemTitle,
                                    description = description,
                                    tripId = viewModel.tripId,
                                    date = date
                                )
                            )
                        }
                        showItineraryItemDialog = false
                    }
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                Button(onClick = { showItineraryItemDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

}


@Composable
fun ItineraryItemCard(
    itineraryItem: ItineraryItem,
    onEdit: () -> Unit,
    onDelete: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(color = colorResource(icon_color), shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Titulo: ${itineraryItem.title}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Descripción: ${itineraryItem.description}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Fecha: ${itineraryItem.date}", style = MaterialTheme.typography.bodyMedium)
            Row {
                IconButton(onEdit) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Editar Itinerario"
                    )
                }
                IconButton(onDelete) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Eliminar Itinerario"
                    )
                }
            }

        }
    }
}