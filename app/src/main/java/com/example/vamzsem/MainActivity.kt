package com.example.vamzsem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.vamzsem.food_database.*
import com.example.vamzsem.ui.theme.VamzSemTheme
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Room database
        val db = Room.databaseBuilder(
            applicationContext,
            FoodDatabase::class.java,
            "food_database"
        ).build()

        val foodRepository = FoodRepository(db.dao)
        val userRepository = UserRepository(db.userDao)
        val factory = FoodViewModelFactory(foodRepository, userRepository)

        setContent {
            VamzSemTheme {
                val windowInfo = rememberWindowInfo()
                val foodViewModel: FoodViewModel = viewModel(factory = factory)
                AppContent(windowInfo, foodViewModel)
              //  CalorieCounter(foodViewModel)

            }
        }
    }
}


@Composable
fun AppContent(
    windowInfo: WindowInfo,
    foodViewModel: FoodViewModel

) {

    var showDialog by remember { mutableStateOf(false) }

    MenuLayout(windowInfo = windowInfo) {
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
                    // Calorie Counter
                   // CalorieCounter()
                    CalorieCounter(viewModel = foodViewModel)
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    // Add FoodButton
                    FoodList(foodViewModel)
                  //  AddFoodButton(onClick = { showDialog = true })
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp).padding(vertical = 25.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        // Add FoodButton
                        AddFoodButton(onClick = { showDialog = true })
                    }
                }

                // Food List

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
                    // Calorie Counter
                   // CalorieCounter()
                    CalorieCounter(viewModel = foodViewModel)

                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(16.dp)
                ) {
                    // Add FoodButton
                    FoodList(foodViewModel)
                  //  AddFoodButton(onClick = { showDialog = true })
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp).padding(vertical = 25.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        // Add FoodButton
                        AddFoodButton(onClick = { showDialog = true })
                    }

                }

                // Food List


            }
        }

        if (showDialog) {
            AddFoodDialog(
                onDismiss = { showDialog = false },
                onConfirm = { name, type, calories ->
                    val newFood = Food(
                        userId = "1", // Replace with actual userId
                        nameOfFood = name,
                        typeOfFood = type,
                        calories = calories,
                        date = Calendar.getInstance().time
                    )
                    foodViewModel.insertFood(newFood)
                }
            )
        }
    }
}
