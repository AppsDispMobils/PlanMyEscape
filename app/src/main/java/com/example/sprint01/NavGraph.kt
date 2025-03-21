package com.example.sprint01

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.sprint01.ui.view.SettingsScreen
import com.example.sprint01.ui.view.LoginScreen
import com.example.sprint01.ui.view.HomeScreen
import com.example.sprint01.ui.view.ProfileScreen

import com.example.sprint01.ui.view.AboutUsScreen
import com.example.sprint01.ui.view.TermsConditionsScreen
import com.example.sprint01.ui.view.ProgrammedTripsScreen
import com.example.sprint01.ui.view.PlanNewTripScreen
import com.example.sprint01.ui.view.TripDetailsScreen


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable ("login") { LoginScreen(navController) }
        composable ("home") { HomeScreen(navController) }
        composable ("profile") { ProfileScreen(navController) }
        composable("settings"){SettingsScreen(navController)}
        composable ("aboutUs") { AboutUsScreen(navController) }
        composable ("termsConditions") { TermsConditionsScreen(navController) }
        composable ("programmedTrips") { ProgrammedTripsScreen(navController) }
        composable ("planNewTrip") { PlanNewTripScreen(navController) }
        composable(
            route = "tripDetails/{tripId}",
            arguments = listOf(navArgument("tripId") { type = NavType.IntType})
        ) {
            TripDetailsScreen(navController = navController)
        }
    }

}
