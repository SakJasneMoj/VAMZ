package com.example.vamzsem

import FoodViewModel
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    foodViewModel: FoodViewModel = viewModel(),
    timerViewModel: TimerViewModel = viewModel()
) {
    NavHost(navController = navController, startDestination = Screen.Calories.route) {
        composable(Screen.Calories.route) {
            CaloriesScreen(foodViewModel = foodViewModel, navController = navController)
        }
        composable(Screen.Sports.route) {
            SportsScreen(navController = navController, timerViewModel = timerViewModel)
        }
        composable(Screen.Calendar.route) {
            CalendarScreen(navController = navController, timerViewModel = timerViewModel)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }
        composable("activities") {
            ActivityListScreen(timerViewModel = timerViewModel)
        }
    }
}
