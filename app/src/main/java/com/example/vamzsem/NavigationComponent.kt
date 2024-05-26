package com.example.vamzsem


import ProfileViewModel

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
    val profileViewModel: ProfileViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = Screen.Calories.route) {
        composable(Screen.Calories.route) {
            CaloriesScreen(navController = navController, foodViewModel = foodViewModel, profileViewModel = profileViewModel)
        }
        composable(Screen.Sports.route) {
            SportsScreen(navController = navController, timerViewModel = timerViewModel, profileViewModel = profileViewModel)
        }
        composable(Screen.Calendar.route) {
            CalendarScreen(navController = navController, timerViewModel = timerViewModel, foodViewModel = foodViewModel, profileViewModel = profileViewModel)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController, profileViewModel = profileViewModel)
        }
    }
}
