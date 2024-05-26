package com.example.vamzsem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.vamzsem.ui.theme.VamzSemTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = application as MyApplication
        val foodRepository = app.foodRepository
        val userRepository = app.userRepository
        val timerRepository = app.timerRepository

        val foodViewModelFactory = FoodViewModelFactory(foodRepository, userRepository, applicationContext)
        val timerViewModelFactory = TimerViewModelFactory(application, timerRepository, foodRepository)

        val foodViewModel: FoodViewModel by viewModels { foodViewModelFactory }
        val timerViewModel: TimerViewModel by viewModels { timerViewModelFactory }

        setContent {
            VamzSemTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController, foodViewModel = foodViewModel, timerViewModel = timerViewModel)
            }
        }
    }
}
