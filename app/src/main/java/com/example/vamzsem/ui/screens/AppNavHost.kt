package com.example.vamzsem.ui.screens


import com.example.vamzsem.viewModel.ProfileViewModel
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vamzsem.ui.components.ActivityListScreen
import com.example.vamzsem.viewModel.FoodViewModel
import com.example.vamzsem.viewModel.TimerViewModel

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    foodViewModel: FoodViewModel = viewModel(),
    timerViewModel: TimerViewModel = viewModel(),
    profileViewModel: ProfileViewModel = viewModel()
) {
    NavHost(navController = navController, startDestination = Screen.Calories.route) {
        composable(Screen.Calories.route) {
            CaloriesScreen(foodViewModel = foodViewModel, navController = navController, profileViewModel = profileViewModel)
        }
        composable(Screen.Sports.route) {
            SportsScreen(navController = navController, timerViewModel = timerViewModel,  profileViewModel = profileViewModel)
        }
        composable(Screen.Calendar.route) {
            CalendarScreen(navController = navController, timerViewModel = timerViewModel, foodViewModel = foodViewModel,profileViewModel = profileViewModel)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController, profileViewModel = profileViewModel)
        }
        composable("activities") {
            ActivityListScreen(timerViewModel = timerViewModel)
        }
    }
}
