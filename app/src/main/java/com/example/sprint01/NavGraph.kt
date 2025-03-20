package com.example.sprint01

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.sprint01.ui.screens.LoginScreen
import com.example.sprint01.ui.screens.HomeScreen
import com.example.sprint01.ui.screens.ProfileScreen
import com.example.sprint01.ui.screens.SettingsScreen
import com.example.sprint01.ui.screens.AboutUsScreen
import com.example.sprint01.ui.screens.TermsConditionsScreen
import com.example.sprint01.ui.view.ProgrammedTripsScreen
import com.example.sprint01.ui.screens.PlanNewTripScreen
import com.example.sprint01.ui.view.TripDetailsScreen


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable ("login") { LoginScreen(navController)}
        composable ("home") { HomeScreen(navController)}
        composable ("profile") { ProfileScreen(navController)}
        composable ("settings") { SettingsScreen(navController) }
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
