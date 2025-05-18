package com.example.PlanMyEscape

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.PlanMyEscape.domain.model.Trip
import com.example.PlanMyEscape.ui.view.SettingsScreen
import com.example.PlanMyEscape.ui.view.LoginScreen
import com.example.PlanMyEscape.ui.view.HomeScreen
import com.example.PlanMyEscape.ui.view.ProfileScreen

import com.example.PlanMyEscape.ui.view.AboutUsScreen
import com.example.PlanMyEscape.ui.view.BookScreen
import com.example.PlanMyEscape.ui.view.GalleryScreen
import com.example.PlanMyEscape.ui.view.HotelDetailsScreen
import com.example.PlanMyEscape.ui.view.TermsConditionsScreen
import com.example.PlanMyEscape.ui.view.ProgrammedTripsScreen
import com.example.PlanMyEscape.ui.view.PlanNewTripScreen
import com.example.PlanMyEscape.ui.view.SignupScreen
import com.example.PlanMyEscape.ui.view.TripDetailsScreen
import com.example.PlanMyEscape.ui.view.RecoverPasswordScreen
import com.example.PlanMyEscape.ui.viewmodel.GalleryViewModel


@Composable
fun NavGraph(navController: NavHostController) {


    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignupScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("book") { BookScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("settings") { SettingsScreen(navController) }
        composable("aboutUs") { AboutUsScreen(navController) }
        composable("termsConditions") { TermsConditionsScreen(navController) }
        composable("programmedTrips") { ProgrammedTripsScreen(navController) }
        composable("planNewTrip") { PlanNewTripScreen(navController) }
        composable(
            route = "hotelDetails/{hotelId}/{startDate}/{endDate}",
            arguments = listOf(
                navArgument("hotelId") { type = NavType.StringType },
                navArgument("startDate") { type = NavType.StringType },
                navArgument("endDate") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val hotelId = backStackEntry.arguments?.getString("hotelId") ?: ""
            val startDate = backStackEntry.arguments?.getString("startDate") ?: ""
            val endDate = backStackEntry.arguments?.getString("endDate") ?: ""
            HotelDetailsScreen(
                hotelId = hotelId,
                startDate = startDate,
                endDate = endDate,
                navController = navController
            )
        }
        composable(
            route = "tripDetails/{tripId}/{tripStartDate}/{tripEndDate}",
            arguments = listOf(
                navArgument("tripId") { type = NavType.IntType },
                navArgument("tripStartDate") { type = NavType.StringType },
                navArgument("tripEndDate") { type = NavType.StringType }),
        ) { backStackEntry ->
            val tripStartDate = backStackEntry.arguments?.getString("tripStartDate") ?: ""
            val tripEndDate = backStackEntry.arguments?.getString("tripEndDate") ?: ""
            TripDetailsScreen(
                navController,
                tripStartDate = tripStartDate,
                tripEndDate = tripEndDate
            )
        }
        composable("recover") { RecoverPasswordScreen(navController) }
        composable("gallery/{tripId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId")!!.toInt()
            val viewModel: GalleryViewModel = viewModel()
            val trips by viewModel.trips.collectAsState()
            val currentTrip = remember(trips) { mutableStateOf<Trip?>(null) }
            LaunchedEffect(trips, tripId) {
                currentTrip.value = trips.find { it.id == tripId }
            }

            currentTrip.value?.let { trip ->
                GalleryScreen(
                    trip = trip,
                    onBack = { navController.popBackStack() },
                    onAddImage = { uri -> viewModel.addImage(tripId, uri) },
                    onDeleteImage = { uri -> viewModel.deleteImage(uri) },
                    navController = navController
                )
            }
        }
    }
}



