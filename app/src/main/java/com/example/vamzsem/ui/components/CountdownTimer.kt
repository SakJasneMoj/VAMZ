package com.example.vamzsem.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.vamzsem.viewModel.TimerViewModel
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
fun CountdownTimer(timerViewModel: TimerViewModel, maxTimeInMinutes: Int = 100, date: String) {
    val isRunning by timerViewModel.isRunning.collectAsState()
    val totalTimeInSeconds by timerViewModel.totalTimeInSeconds.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val maxTimeInSeconds = maxTimeInMinutes * 60
    var sessionStartTime by remember { mutableStateOf(maxTimeInSeconds) }
    var adjustedMaxTimeInSeconds by remember { mutableStateOf(maxTimeInSeconds) }

    LaunchedEffect(date) {
        timerViewModel.getTotalTimeSpentForDate(date) { totalTimeSpent ->
            adjustedMaxTimeInSeconds = maxTimeInSeconds - totalTimeSpent.toInt()
            sessionStartTime = adjustedMaxTimeInSeconds
            timerViewModel.setTimer(adjustedMaxTimeInSeconds)
        }
    }

    LaunchedEffect(isRunning) {
        while (isRunning && totalTimeInSeconds > 0) {
            delay(1000L)
            timerViewModel.setTimer(totalTimeInSeconds - 1)
        }
        if (totalTimeInSeconds == 0) {
            showDialog = true
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(200.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawArc(
                    color = Color.Blue,
                    startAngle = -90f,
                    sweepAngle = (totalTimeInSeconds / adjustedMaxTimeInSeconds.toFloat()) * 360,
                    useCenter = false,
                    style = Stroke(width = 8.dp.toPx())
                )
            }
            Text(
                text = String.format(
                    "%02d:%02d",
                    TimeUnit.SECONDS.toMinutes(totalTimeInSeconds.toLong()),
                    totalTimeInSeconds % 60
                ),
                style = MaterialTheme.typography.h3
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(onClick = {
                if (isRunning) {
                    timerViewModel.stopTimer()
                } else {
                    sessionStartTime = totalTimeInSeconds
                    timerViewModel.startTimer()
                }
            }) {
                Text(if (isRunning) "Pause" else "Start")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = {
                timerViewModel.stopTimer()
                showDialog = true
            }) {
                Text("Stop")
            }
        }

        if (showDialog) {
            ActivityNameDialog(onDismiss = { showDialog = false }, onConfirm = { activityName ->
                val timeSpent = sessionStartTime - totalTimeInSeconds
                timerViewModel.saveTimerData("1", activityName, timeSpent.toLong())
                sessionStartTime = totalTimeInSeconds
                showDialog = false
            })
        }
    }
}
