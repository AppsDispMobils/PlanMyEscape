package com.example.PlanMyEscape.ui.view

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.PlanMyEscape.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    var selectedIndex by remember { mutableStateOf(0) }

    // Obtén el color desde los recursos
    val appColor = colorResource(id = R.color.app_color)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.title),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Center
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = appColor,
                    titleContentColor = Color.White // Color del texto del título
                ),
                actions = {
                    IconButton(
                        onClick = {
                            Log.d("HomeScreen", "Navigating to settings screen")
                            navController.navigate("settings")
                        }
                    ) {
                        Icon(
                            Icons.Outlined.Settings,
                            contentDescription = "settings",
                            tint = Color.Gray // Color del ícono
                        )
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
                    .background(Color.White) // Fondo blanco para el contenido
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Botón "Programmed Trips"
                IconButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f)
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                        .border(2.dp, appColor, RoundedCornerShape(24.dp)) // Borde naranja pastel
                        .background(appColor.copy(alpha = 0.2f)), // Fondo semitransparente
                    onClick = {
                        Log.d("HomeScreen", "Navigating to programmed trips screen")
                        navController.navigate("programmedTrips")
                    }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.ProgrammedTrips),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )
                    }
                }

                // Botón "Plan New Trip"
                IconButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f)
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                        .border(2.dp, appColor, RoundedCornerShape(24.dp))
                        .background(appColor.copy(alpha = 0.2f)),
                    onClick = {
                        Log.d("HomeScreen", "Navigating to plan new trip screen")
                        navController.navigate("planNewTrip")
                    }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.Plan_New_Trip),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )
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
    NavigationBar(
        containerColor = colorResource(id = R.color.app_color) // Color de fondo del BottomNavigationBar
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = Color.White // Color del ícono
                )
            },
            label = {
                Text(
                    text = stringResource(id=R.string.Profile),
                    color = Color.Gray // Color del texto
                )
            },
            selected = selectedIndex == 0,
            onClick = {
                Log.d("BottomNavigation", "Navigating to profile screen")
                navController.navigate("profile")
            }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Outlined.Home,
                    contentDescription = "Home",
                    tint = Color.Gray // Color del ícono
                )
            },
            label = {
                Text(
                    text = stringResource(id = R.string.Home),
                    color = Color.Gray // Color del texto
                )
            },
            selected = selectedIndex == 1,
            onClick = {
                Log.d("BottomNavigation", "Navigating to home screen")
                navController.navigate("home")
            }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Outlined.Search,
                    contentDescription = "Book",
                    tint = Color.Gray
                )
            },
            label = {
                Text(
                    text = stringResource(id = R.string.Book),
                    color = Color.Gray
                )
            },
            selected = selectedIndex == 2,
            onClick = {
                Log.d("BottomNavigation", "Navigating to home screen")
                navController.navigate("book")
            }
        )
    }
}
