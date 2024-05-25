package com.example.vamzsem

import DateUtils
import FoodViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vamzsem.food_database.Food
import com.example.vamzsem.ui.theme.VamzSemTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get application instance to access repositories
        val app = application as MyApplication
        val foodRepository = app.foodRepository
        val userRepository = app.userRepository

        // ViewModel factory
        val factory = FoodViewModelFactory(foodRepository, userRepository)

        val foodViewModel = ViewModelProvider(this, factory).get(FoodViewModel::class.java)
        lifecycle.addObserver(foodViewModel)  // Add this line to register the observer


        setContent {
            VamzSemTheme {
                val windowInfo = rememberWindowInfo()
                val foodViewModel: FoodViewModel = viewModel(factory = factory)
                val context = LocalContext.current
                val dateUtils = DateUtils(context)

                LaunchedEffect(Unit) {
                    if (dateUtils.isNewDay()) {
                        withContext(Dispatchers.Main) {
                            foodViewModel.fetchFoods()
                            dateUtils.saveCurrentDate()
                        }
                    }
                }
                AppContent(windowInfo, foodViewModel)
            }
        }
    }
}

@Composable
fun AppContent(
    windowInfo: WindowInfo,
    foodViewModel: FoodViewModel
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(foodViewModel)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(foodViewModel)
        }
    }

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
                    CalorieCounter(viewModel = foodViewModel)
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
                    CalorieCounter(viewModel = foodViewModel)
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
