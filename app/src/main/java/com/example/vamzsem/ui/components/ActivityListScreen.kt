package com.example.vamzsem

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vamzsem.viewModel.TimerViewModel

@Composable
fun ActivityListScreen(timerViewModel: TimerViewModel) {
    val activities by timerViewModel.activities.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Today's Activities",
            style = MaterialTheme.typography.h6,
            fontSize = 24.sp
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(activities) { activity ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${activity.activityName} - ${activity.timeSpent / 60} min ${activity.timeSpent % 60} sec",
                            style = MaterialTheme.typography.body1,
                            fontSize = 20.sp,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                timerViewModel.removeActivity(activity)
                            },
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) {
                            Text(text = "Remove")
                        }
                    }
                }
            }
        }
    }
}
