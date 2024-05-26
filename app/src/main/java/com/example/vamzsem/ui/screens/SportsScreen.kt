package com.example.vamzsem.ui.screens

import com.example.vamzsem.viewModel.ProfileViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.vamzsem.ui.components.ActivityListScreen
import com.example.vamzsem.ui.components.CountdownTimer
import com.example.vamzsem.ui.components.MenuLayout
import com.example.vamzsem.ui.utils.WindowInfo
import com.example.vamzsem.ui.utils.rememberWindowInfo
import com.example.vamzsem.viewModel.TimerViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun SportsScreen(
    navController: NavHostController,
    timerViewModel: TimerViewModel,
    profileViewModel: ProfileViewModel
) {
    val windowInfo = rememberWindowInfo()
    val exerciseMaxTime by profileViewModel.exerciseMaxTime.collectAsState()
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val today = sdf.format(Date())
    val coroutineScope = rememberCoroutineScope()
    var totalExerciseTime by remember { mutableStateOf(0L) }

    LaunchedEffect(today) {
        coroutineScope.launch {
            timerViewModel.getTotalTimeSpentForDate(today) { totalTime ->
                totalExerciseTime = totalTime
            }
        }
    }

    MenuLayout(windowInfo = windowInfo, navController = navController, profileViewModel = profileViewModel) {
        if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    CountdownTimer(
                        timerViewModel = timerViewModel,
                        maxTimeInMinutes = (exerciseMaxTime * 60 - totalExerciseTime.toInt()) / 60,
                        date = today
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    ActivityListScreen(timerViewModel = timerViewModel)
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
                        .fillMaxHeight()
                ) {
                    CountdownTimer(
                        timerViewModel = timerViewModel,
                        maxTimeInMinutes = (exerciseMaxTime * 60 - totalExerciseTime.toInt()) / 60,
                        date = today
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(16.dp)
                ) {
                    ActivityListScreen(timerViewModel = timerViewModel)
                }
            }
        }
    }
}
