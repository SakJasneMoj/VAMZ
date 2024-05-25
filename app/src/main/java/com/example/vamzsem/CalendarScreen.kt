package com.example.vamzsem

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.navigation.NavController

@Composable
fun CalendarScreen(navController: NavController) {
    Column {
        Text(text = "Calendar Screen")
        Button(onClick = { navController.navigate(Screen.Settings.route) }) {
            Text("Go to Settings")
        }
    }
}
