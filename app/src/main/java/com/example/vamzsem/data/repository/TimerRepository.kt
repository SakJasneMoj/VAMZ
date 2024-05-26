package com.example.vamzsem

import android.content.Context
import androidx.annotation.WorkerThread
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TimerRepository(private val timerDao: TimerDao, private val context: Context) {

    @WorkerThread
    suspend fun insert(timerEntity: TimerEntity) {
        timerDao.insert(timerEntity)
    }

    @WorkerThread
    suspend fun delete(timerEntity: TimerEntity) {
        timerDao.delete(timerEntity)
    }

    suspend fun getTimersForDate(date: String): List<TimerEntity> {
        return timerDao.getTimersForDate(date)
    }

    suspend fun getTodayTimers(): List<TimerEntity> {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = sdf.format(Date())
        return timerDao.getTimersForDate(currentDate)
    }

    fun getSavedTime(): Int {
        val sharedPref = context.getSharedPreferences("timer_prefs", Context.MODE_PRIVATE)
        return sharedPref.getInt("saved_time", 100 * 60) // Default to max time
    }

    fun isTimerRunning(): Boolean {
        val sharedPref = context.getSharedPreferences("timer_prefs", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("is_running", false)
    }

    fun saveTime(seconds: Int) {
        val sharedPref = context.getSharedPreferences("timer_prefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putInt("saved_time", seconds)
            apply()
        }
    }

    fun saveTimerRunningState(isRunning: Boolean) {
        val sharedPref = context.getSharedPreferences("timer_prefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("is_running", isRunning)
            apply()
        }
    }

    suspend fun getTotalTimeSpentForDate(date: String): Long {
        return timerDao.getTotalTimeSpentForDate(date) ?: 0L
    }


}
