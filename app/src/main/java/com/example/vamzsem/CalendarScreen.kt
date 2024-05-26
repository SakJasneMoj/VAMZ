package com.example.vamzsem

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun CalendarScreen(navController: NavHostController, timerViewModel: TimerViewModel) {
    val windowInfo = rememberWindowInfo()
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf("") }
    var totalExerciseTime by remember { mutableStateOf(0L) }
    var totalCalories by remember { mutableStateOf(0L) }

    MenuLayout(windowInfo = windowInfo, navController = navController) {
        if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    // Date Picker Button
                    Button(onClick = {
                        showDatePicker(context) { date ->
                            selectedDate = date
                            // Fetch total exercise time and calories based on selected date
                            timerViewModel.getTimersForDate(date) { activities ->
                                totalExerciseTime = activities.sumOf { it.timeSpent }
                                totalCalories = activities.sumOf { it.caloriesBurned } // Assume `caloriesBurned` is a property in TimerEntity
                            }
                        }
                    }) {
                        Text(text = "Select Date")
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Display selected date
                        Text(text = "Selected Date: $selectedDate", style = MaterialTheme.typography.h6)

                        Spacer(modifier = Modifier.height(32.dp))

                        // Counters for Exercise Time and Calories
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            CounterBox(title = "Total Exercise Time", value = formatTime(totalExerciseTime))
                            CounterBox(title = "Total Calories Burned", value = "$totalCalories cal")
                        }
                    }
                }
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    // Date Picker Button
                    Button(onClick = {
                        showDatePicker(context) { date ->
                            selectedDate = date
                            // Fetch total exercise time and calories based on selected date
                            timerViewModel.getTimersForDate(date) { activities ->
                                totalExerciseTime = activities.sumOf { it.timeSpent }
                                totalCalories = activities.sumOf { it.caloriesBurned } // Assume `caloriesBurned` is a property in TimerEntity
                            }
                        }
                    }) {
                        Text(text = "Select Date")
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Display selected date
                        Text(text = "Selected Date: $selectedDate", style = MaterialTheme.typography.h6)

                        Spacer(modifier = Modifier.height(32.dp))

                        // Counters for Exercise Time and Calories
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            CounterBox(title = "Total Exercise Time", value = formatTime(totalExerciseTime))
                            CounterBox(title = "Total Calories Burned", value = "$totalCalories cal")
                        }
                    }
                }
            }
        }
    }
}
