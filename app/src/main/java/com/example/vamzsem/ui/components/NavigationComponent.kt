package com.example.vamzsem.ui.components


import com.example.vamzsem.viewModel.ProfileViewModel

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vamzsem.ui.screens.CalendarScreen
import com.example.vamzsem.ui.screens.CaloriesScreen
import com.example.vamzsem.ui.screens.Screen
import com.example.vamzsem.ui.screens.SettingsScreen
import com.example.vamzsem.ui.screens.SportsScreen
import com.example.vamzsem.viewModel.FoodViewModel
import com.example.vamzsem.viewModel.TimerViewModel

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
