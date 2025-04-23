package com.example.PlanMyEscape

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.PlanMyEscape.ui.view.SettingsScreen
import com.example.PlanMyEscape.ui.view.LoginScreen
import com.example.PlanMyEscape.ui.view.HomeScreen
import com.example.PlanMyEscape.ui.view.ProfileScreen

import com.example.PlanMyEscape.ui.view.AboutUsScreen
import com.example.PlanMyEscape.ui.view.TermsConditionsScreen
import com.example.PlanMyEscape.ui.view.ProgrammedTripsScreen
import com.example.PlanMyEscape.ui.view.PlanNewTripScreen
import com.example.PlanMyEscape.ui.view.SignupScreen
import com.example.PlanMyEscape.ui.view.TripDetailsScreen


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable ("login") { LoginScreen(navController) }
        composable ("signup") { SignupScreen(navController) }
        composable ("home") { HomeScreen(navController) }
        composable ("profile") { ProfileScreen(navController) }
        composable("settings"){SettingsScreen(navController)}
        composable ("aboutUs") { AboutUsScreen(navController) }
        composable ("termsConditions") { TermsConditionsScreen(navController) }
        composable ("programmedTrips") { ProgrammedTripsScreen(navController) }
        composable ("planNewTrip") { PlanNewTripScreen(navController) }
        composable(
            route =  "tripDetails/{tripId}/{tripStartDate}/{tripEndDate}",
            arguments = listOf(
                navArgument("tripId") { type = NavType.IntType },
                navArgument("tripStartDate") { type = NavType.StringType },
                navArgument("tripEndDate") { type = NavType.StringType })
        ) {
            backStackEntry ->
            val tripStartDate = backStackEntry.arguments?.getString("tripStartDate") ?: ""
            val tripEndDate = backStackEntry.arguments?.getString("tripEndDate") ?: ""
            TripDetailsScreen(navController, tripStartDate = tripStartDate, tripEndDate = tripEndDate)
        }
    }

}
