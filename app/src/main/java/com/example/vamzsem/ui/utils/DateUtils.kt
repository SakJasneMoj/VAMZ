package com.example.vamzsem.ui.utils

import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.*

class DateUtils(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun getCurrentDate(): String {
        return dateFormat.format(Date())
    }

    fun getSavedDate(): String? {
        return sharedPreferences.getString("saved_date", null)
    }

    fun saveCurrentDate() {
        sharedPreferences.edit().putString("saved_date", getCurrentDate()).apply()
    }

    fun isNewDay(): Boolean {
        val savedDate = getSavedDate()
        val currentDate = getCurrentDate()
        return savedDate == null || savedDate != currentDate
    }
}
