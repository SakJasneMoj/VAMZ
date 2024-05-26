package com.example.vamzsem


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NavigationComponent() {
    val navController = rememberNavController()
    val foodViewModel: FoodViewModel = hiltViewModel()
    val timerViewModel: TimerViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = Screen.Calories.route) {
        composable(Screen.Calories.route) {
            CaloriesScreen(navController = navController, foodViewModel = foodViewModel)
        }
        composable(Screen.Sports.route) {
            SportsScreen(navController = navController, timerViewModel = timerViewModel)
        }
        composable(Screen.Calendar.route) {
            CalendarScreen(navController = navController, timerViewModel = timerViewModel, foodViewModel = foodViewModel)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }
    }
}
