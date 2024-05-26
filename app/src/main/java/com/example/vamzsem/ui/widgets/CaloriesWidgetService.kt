package com.example.vamzsem.ui.widgets

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.vamzsem.MyApplication
import com.example.vamzsem.R
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.firstOrNull
import java.text.SimpleDateFormat
import java.util.*

class CaloriesWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return CaloriesRemoteViewsFactory(applicationContext)
    }
}

class CaloriesRemoteViewsFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {

    private var calories: Int = 0

    override fun onCreate() {
        // Do nothing
    }

    override fun onDataSetChanged() {
        calories = getCaloriesForToday(context)
    }

    override fun onDestroy() {
        // Do nothing
    }

    override fun getCount(): Int = 1

    override fun getViewAt(position: Int): RemoteViews {
        val remoteViews = RemoteViews(context.packageName, R.layout.widget_calories)
        remoteViews.setTextViewText(R.id.calories_text, "Calories: $calories cal")
        return remoteViews
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = position.toLong()

    override fun hasStableIds(): Boolean = true

    private fun getCaloriesForToday(context: Context): Int {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = sdf.format(Date())
        val app = context.applicationContext as MyApplication
        val foodRepository = app.foodRepository

        return runBlocking {
            val totalCaloriesFlow = foodRepository.getTotalCaloriesByUserAndDate("1", today)
            val totalCalories = totalCaloriesFlow.firstOrNull() ?: 0
            totalCalories
        }
    }
}
