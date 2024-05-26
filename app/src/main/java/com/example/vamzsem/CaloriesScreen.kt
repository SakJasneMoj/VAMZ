package com.example.vamzsem

import ProfileViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.vamzsem.food_database.Food
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CaloriesScreen(
    navController: NavHostController,
    foodViewModel: FoodViewModel,
    profileViewModel: ProfileViewModel
) {
    var showDialog by remember { mutableStateOf(false) }
    val windowInfo = rememberWindowInfo()

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
                    CalorieCounter(foodViewModel = foodViewModel, profileViewModel = profileViewModel)
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    FoodList(foodViewModel)
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp)
                            .padding(vertical = 25.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        AddFoodButton(onClick = { showDialog = true })
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
                        .fillMaxHeight()
                ) {
                    CalorieCounter(foodViewModel = foodViewModel, profileViewModel = profileViewModel)
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(16.dp)
                ) {
                    FoodList(foodViewModel)
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp)
                            .padding(vertical = 25.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        AddFoodButton(onClick = { showDialog = true })
                    }
                }
            }
        }

        if (showDialog) {
            AddFoodDialog(
                onDismiss = { showDialog = false },
                onConfirm = { name, type, calories ->
                    val newFood = Food(
                        userId = "1",
                        nameOfFood = name,
                        typeOfFood = type,
                        calories = calories,
                        date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                    )
                    foodViewModel.insertFood(newFood)
                }
            )
        }
    }
}
