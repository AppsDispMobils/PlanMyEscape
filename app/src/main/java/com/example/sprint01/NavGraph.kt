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
import com.example.sprint01.ui.screens.ExploreScreen
import com.example.sprint01.ui.screens.TermsConditionsScreen
import com.example.sprint01.ui.screens.ProgrammedTripsScreen
import com.example.sprint01.ui.screens.PlanNewTripScreen
import com.example.sprint01.ui.screens.Trip
import com.example.sprint01.ui.screens.TripDetailsScreen


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
        composable ("explore") { ExploreScreen(navController) }
        composable ("tripDetails") { TripDetailsScreen(navController, null) }
    }

}
