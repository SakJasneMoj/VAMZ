package com.example.vamzsem.ui.screens

import com.example.vamzsem.ui.utils.WindowInfo
import com.example.vamzsem.viewModel.ProfileViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vamzsem.ui.components.MenuLayout
import com.example.vamzsem.ui.utils.rememberWindowInfo

@Composable
fun SettingsScreen(navController: NavHostController, profileViewModel: ProfileViewModel) {
    val windowInfo = rememberWindowInfo()

    MenuLayout(windowInfo = windowInfo, navController = navController, profileViewModel = profileViewModel) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item { ProfileSection(profileViewModel) }
            item { SettingsSection(profileViewModel) }
            item { NotificationsSection(profileViewModel) }
        }
    }
}
@Composable
fun ProfileSection(profileViewModel: ProfileViewModel) {
    val profileName by profileViewModel.profileName.collectAsState()
    var showNameDialog by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = profileName, fontSize = 24.sp, color = Color.Black)
        IconButton(onClick = { showNameDialog = true }) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Profile")
        }
    }

    if (showNameDialog) {
        var newName by remember { mutableStateOf(profileName) }

        AlertDialog(
            onDismissRequest = { showNameDialog = false },
            title = { Text(text = "Edit Name") },
            text = {
                TextField(value = newName, onValueChange = { newName = it })
            },
            confirmButton = {
                Button(onClick = {
                    profileViewModel.updateProfileName(newName)
                    showNameDialog = false
                }) {
                    Text(text = "Save")
                }
            },
            dismissButton = {
                Button(onClick = { showNameDialog = false }) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}

@Composable
fun SettingsSection(profileViewModel: ProfileViewModel) {
    val exerciseMaxTime by profileViewModel.exerciseMaxTime.collectAsState()
    val caloriesMaxValue by profileViewModel.caloriesMaxValue.collectAsState()

    var newExerciseMaxTime by remember { mutableStateOf(exerciseMaxTime.toString()) }
    var newCaloriesMaxValue by remember { mutableStateOf(caloriesMaxValue.toString()) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        // Exercise Max Time
        Text(text = "Exercise Max Time (minutes)", style = MaterialTheme.typography.h6)
        TextField(
            value = newExerciseMaxTime,
            onValueChange = { newExerciseMaxTime = it },
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Button(onClick = {
            val time = newExerciseMaxTime.toIntOrNull()
            if (time != null) {
                profileViewModel.updateExerciseMaxTime(time)
            }
        }) {
            Text(text = "Save")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Calories Max Value
        Text(text = "Calories Max Value", style = MaterialTheme.typography.h6)
        TextField(
            value = newCaloriesMaxValue,
            onValueChange = { newCaloriesMaxValue = it },
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Button(onClick = {
            val value = newCaloriesMaxValue.toIntOrNull()
            if (value != null) {
                profileViewModel.updateCaloriesMaxValue(value)
            }
        }) {
            Text(text = "Save")
        }
    }
}

@Composable
fun NotificationsSection(profileViewModel: ProfileViewModel) {
    val drinkWaterNotification by profileViewModel.drinkWaterNotification.collectAsState()
    val caloriesIntakeNotification by profileViewModel.caloriesIntakeNotification.collectAsState()
    val exerciseReminderNotification by profileViewModel.exerciseReminderNotification.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Notifications", style = MaterialTheme.typography.h4)

        Spacer(modifier = Modifier.height(24.dp))

        // Drink Water Notification
        NotificationRow(
            label = "Drink Water Reminder",
            isChecked = drinkWaterNotification,
            onCheckedChange = { profileViewModel.toggleDrinkWaterNotification() }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Calories Intake Notification
        NotificationRow(
            label = "Calories Intake Reminder",
            isChecked = caloriesIntakeNotification > 0,
            onCheckedChange = {
                if (it) profileViewModel.updateCaloriesIntakeNotification(1)
                else profileViewModel.updateCaloriesIntakeNotification(0)
            }
        )

        if (caloriesIntakeNotification > 0) {
            FrequencySelector(
                selectedFrequency = caloriesIntakeNotification,
                onFrequencySelected = { profileViewModel.updateCaloriesIntakeNotification(it) }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Exercise Reminder Notification
        NotificationRow(
            label = "Exercise Reminder",
            isChecked = exerciseReminderNotification,
            onCheckedChange = { profileViewModel.toggleExerciseReminderNotification() }
        )
    }
}

@Composable
fun NotificationRow(label: String, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = label)
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun FrequencySelector(selectedFrequency: Int, onFrequencySelected: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(onClick = { expanded = true }) {
            Text(text = "$selectedFrequency hour(s)")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listOf(1, 2, 3).forEach { frequency ->
                DropdownMenuItem(onClick = {
                    onFrequencySelected(frequency)
                    expanded = false
                }) {
                    Text(text = "$frequency hour(s)")
                }
            }
        }
    }
}
