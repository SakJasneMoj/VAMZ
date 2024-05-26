package com.example.vamzsem.ui.screens

import com.example.vamzsem.viewModel.ProfileViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.vamzsem.ui.components.InlineDatePicker
import com.example.vamzsem.ui.components.MenuLayout
import com.example.vamzsem.ui.utils.formatTime
import com.example.vamzsem.ui.utils.rememberWindowInfo
import com.example.vamzsem.viewModel.FoodViewModel
import com.example.vamzsem.viewModel.TimerViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CalendarScreen(
    navController: NavHostController,
    timerViewModel: TimerViewModel,
    foodViewModel: FoodViewModel,
    profileViewModel: ProfileViewModel
) {
    val windowInfo = rememberWindowInfo()
    var selectedDate by remember { mutableStateOf("") }
    var totalExerciseTime by remember { mutableStateOf(0L) }
    var totalCalories by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = sdf.format(Date())
        selectedDate = today
        timerViewModel.getTimersForDate(today) { activities ->
            totalExerciseTime = activities.sumOf { it.timeSpent }
        }
        foodViewModel.fetchFoodsByDate(today) { foods ->
            totalCalories = foods.sumOf { it.calories ?: 0 }
        }
    }

    MenuLayout(windowInfo = windowInfo, navController = navController, profileViewModel = profileViewModel) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            InlineDatePicker { date ->
                selectedDate = date
                // Fetch total exercise time and calories based on selected date
                timerViewModel.getTimersForDate(date) { activities ->
                    totalExerciseTime = activities.sumOf { it.timeSpent }
                }
                foodViewModel.fetchFoodsByDate(date) { foods ->
                    totalCalories = foods.sumOf { it.calories ?: 0 }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Selected Date: $selectedDate", style = MaterialTheme.typography.h6)

            Spacer(modifier = Modifier.height(70.dp))

            // Counters for Exercise Time and Calories
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Total Exercise Time", style = MaterialTheme.typography.h5)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = formatTime(totalExerciseTime), style = MaterialTheme.typography.h3)

                Spacer(modifier = Modifier.height(45.dp))

                Text(text = "Total Calories Consumed", style = MaterialTheme.typography.h5)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "$totalCalories cal", style = MaterialTheme.typography.h3)
            }
        }
    }
}
