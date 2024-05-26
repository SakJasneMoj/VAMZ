package com.example.vamzsem

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
            val foodViewModel = FoodViewModel(foodRepository, userRepository, applicationContext)
            foodViewModel.fetchFoods()

            // Manually trigger widget update
            val appWidgetManager = AppWidgetManager.getInstance(applicationContext)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(ComponentName(applicationContext, CaloriesWidgetProvider::class.java))

            // Calculate total calories
            val totalCalories = runBlocking {
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val today = sdf.format(Date())
                foodRepository.getTotalCaloriesByUserAndDate("1", today).first()
            }

            for (appWidgetId in appWidgetIds) {
                CaloriesWidgetProvider.updateAppWidget(applicationContext, appWidgetManager, appWidgetId, totalCalories)
            }
        }
        return Result.success()
    }
}
