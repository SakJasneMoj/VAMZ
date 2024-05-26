package com.example.vamzsem

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class TimerViewModel(
    private val timerRepository: TimerRepository,
    private val foodRepository: FoodRepository,
    application: Application
) : AndroidViewModel(application) {
    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning

    private val _totalTimeInSeconds = MutableStateFlow(timerRepository.getSavedTime())
    val totalTimeInSeconds: StateFlow<Int> = _totalTimeInSeconds

    private val _activities = MutableStateFlow<List<TimerEntity>>(emptyList())
    val activities: StateFlow<List<TimerEntity>> = _activities

    init {
        viewModelScope.launch {
            refreshActivities()
        }
    }

    fun startTimer() {
        _isRunning.value = true
        timerRepository.saveTimerRunningState(true)
    }

    fun stopTimer() {
        _isRunning.value = false
        timerRepository.saveTimerRunningState(false)
    }

    fun setTimer(time: Int) {
        _totalTimeInSeconds.value = time
        timerRepository.saveTime(time)
    }

    fun saveTimerData(userId: String, activityName: String, timeSpent: Long) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = sdf.format(Date())
        viewModelScope.launch {
            timerRepository.insert(TimerEntity(userId = userId, activityName = activityName, timeSpent = timeSpent, date = currentDate))
            refreshActivities()
        }
    }

    private suspend fun refreshActivities() {
        _activities.value = timerRepository.getTodayTimers()
    }

    fun getTimersForDate(date: String, onResult: (List<TimerEntity>) -> Unit) {
        viewModelScope.launch {
            try {
                println("Fetching timers for date: $date")  // Logging the date
                val timers = timerRepository.getTimersForDate(date)
                onResult(timers)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun removeActivity(activity: TimerEntity) {
        viewModelScope.launch {
            try {
                timerRepository.delete(activity)
                _activities.value = timerRepository.getTodayTimers()
                _totalTimeInSeconds.value += activity.timeSpent.toInt()
                timerRepository.saveTime(_totalTimeInSeconds.value)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getTotalCaloriesForDate(date: String, onResult: (Int) -> Unit) {
        viewModelScope.launch {
            try {
                println("Fetching foods for date: $date")  // Logging the date
                val foods = foodRepository.getFoodsForDate(date)
                val totalCalories = foods.sumOf { it.calories }
                onResult(totalCalories)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getTodayData(onResult: (String, List<TimerEntity>, Int) -> Unit) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = sdf.format(Date())
        viewModelScope.launch {
            try {
                val activities = timerRepository.getTimersForDate(today)
                val foods = foodRepository.getFoodsForDate(today)
                val totalCalories = foods.sumOf { it.calories }
                onResult(today, activities, totalCalories)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
