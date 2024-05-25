package com.example.vamzsem

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationComponent() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Calories.route) {
        composable(Screen.Calories.route) {
            CaloriesScreen(navController)
        }
        composable(Screen.Sports.route) {
            SportsScreen(navController)
        }
        composable(Screen.Calendar.route) {
            CalendarScreen(navController)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController)
        }
    }
}
