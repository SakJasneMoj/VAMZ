package com.example.vamzsem

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.navigation.NavController

@Composable
fun CaloriesScreen(navController: NavController) {
    Column {
        Text(text = "Calories Screen")
        Button(onClick = { navController.navigate(Screen.Sports.route) }) {
            Text("Go to Sports")
        }
    }
}
