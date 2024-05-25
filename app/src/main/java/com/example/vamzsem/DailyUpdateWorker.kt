package com.example.vamzsem

import FoodViewModel
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DailyUpdateWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        // Trigger the update for the ViewModel
        withContext(Dispatchers.Main) {
            val application = applicationContext as MyApplication
            val foodRepository = application.foodRepository
            val userRepository = application.userRepository
            val foodViewModel = FoodViewModel(foodRepository, userRepository)
            foodViewModel.fetchFoods()
        }
        return Result.success()
    }
}
