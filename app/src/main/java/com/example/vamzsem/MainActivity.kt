package com.example.vamzsem

import ProfileRepository
import ProfileViewModel
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
        val profileRepository = ProfileRepository(applicationContext)

        val foodViewModelFactory = FoodViewModelFactory(foodRepository, userRepository, application)
        val timerViewModelFactory = TimerViewModelFactory(application, timerRepository, foodRepository)
        val profileViewModelFactory = ProfileViewModelFactory(application, profileRepository)

        val foodViewModel: FoodViewModel by viewModels { foodViewModelFactory }
        val timerViewModel: TimerViewModel by viewModels { timerViewModelFactory }
        val profileViewModel: ProfileViewModel by viewModels { profileViewModelFactory }

        setContent {
            VamzSemTheme {
                val navController = rememberNavController()
                AppNavHost(
                    navController = navController,
                    foodViewModel = foodViewModel,
                    timerViewModel = timerViewModel,
                    profileViewModel = profileViewModel
                )
            }
        }
    }
}
