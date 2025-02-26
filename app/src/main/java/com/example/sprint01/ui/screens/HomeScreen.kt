package com.example.sprint01.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("Destinify", fontSize = 24.sp, textAlign = TextAlign.Center) }
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate("settings")
                        }
                    ) {
                        Icon(Icons.Outlined.Settings, contentDescription = "settings")
                    }
                }

            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedIndex = selectedIndex,
                navController = navController
            )
        },

        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f)
                        .padding(horizontal = 16.dp, vertical = 56.dp)
                        .border(2.dp, Color.Gray, RoundedCornerShape(24.dp)),
                    onClick = {
                        navController.navigate("programmedTrips")
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("Programmed Trips")
                    }
                }
                IconButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f)
                        .padding(horizontal = 16.dp, vertical = 56.dp)
                        .border(2.dp, Color.Gray, RoundedCornerShape(24.dp)),
                    onClick = {
                        navController.navigate("planNewTrip")
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("Plan New Trip")
                    }
                }

            }

        }

    )
}

@Composable
fun BottomNavigationBar(
    selectedIndex: Int,
    navController: NavController
) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.AccountCircle, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = selectedIndex == 0,
            onClick = { navController.navigate("profile")}
            )

        NavigationBarItem(
            icon = { Icon(Icons.Filled.Explore, contentDescription = "Explorar") },
            label = { Text("Explore")},
            selected = selectedIndex == 1,
            onClick = { navController.navigate("explore")}
        )
    }
}