package com.example.vamzsem

import ProfileViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.hilt.navigation.compose.hiltViewModel



@Composable
fun SportsScreen(navController: NavHostController, timerViewModel: TimerViewModel, profileViewModel: ProfileViewModel) {
    val windowInfo = rememberWindowInfo()

    MenuLayout(windowInfo = windowInfo, navController = navController,profileViewModel = profileViewModel) {
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
                    CountdownTimer(timerViewModel = timerViewModel)
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
                    CountdownTimer(timerViewModel = timerViewModel)
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
