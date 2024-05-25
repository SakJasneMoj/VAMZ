package com.example.vamzsem

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.navigation.NavController

@Composable
fun SettingsScreen(navController: NavController) {
    Column {
        Text(text = "Settings Screen")
        Button(onClick = { navController.navigate(Screen.Calories.route) }) {
            Text("Go to Calories")
        }
    }
}
