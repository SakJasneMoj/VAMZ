package com.example.vamzsem.ui.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.example.vamzsem.MyApplication
import com.example.vamzsem.R
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*

class CaloriesWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        updateWidget(context, appWidgetManager, appWidgetIds)
    }

    companion object {
        fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int, totalCalories: Int) {
            val views = RemoteViews(context.packageName, R.layout.widget_calories)
            views.setTextViewText(R.id.calories_text, "Calories: $totalCalories")

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        fun updateWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val today = sdf.format(Date())
            val app = context.applicationContext as MyApplication
            val foodRepository = app.foodRepository

            runBlocking {
                val totalCalories = foodRepository.getTotalCaloriesByUserAndDate("1", today).first()

                for (appWidgetId in appWidgetIds) {
                    updateAppWidget(context, appWidgetManager, appWidgetId, totalCalories)
                }
            }
        }
    }
}
