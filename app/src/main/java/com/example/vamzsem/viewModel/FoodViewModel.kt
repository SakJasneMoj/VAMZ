package com.example.vamzsem

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.vamzsem.data.repository.FoodRepository
import com.example.vamzsem.data.database.foodDatabase.Food
import com.example.vamzsem.data.worker.DailyUpdateWorker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FoodViewModel(
    private val foodRepository: FoodRepository,
    private val userRepository: UserRepository,
    private val context: Context
) : ViewModel(), LifecycleObserver {

    private val _foodList = MutableStateFlow<List<Food>>(emptyList())
    val foodList: StateFlow<List<Food>> = _foodList.asStateFlow()

    private val _totalCalories = MutableStateFlow(0)
    val totalCalories: StateFlow<Int> = _totalCalories.asStateFlow()

    private var currentUserId: String = "1" // For simplicity, use a fixed user ID
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val todayDate: String
        get() = dateFormat.format(Date())

    init {
        fetchFoods()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        fetchFoods()
    }

    fun fetchFoods() {
        viewModelScope.launch {
            foodRepository.getFoodByUserAndDate(currentUserId, todayDate).collect { foods ->
                _foodList.value = foods
                _totalCalories.value = foods.sumOf { it.calories }
            }
        }
    }

    fun insertFood(food: Food) {
        viewModelScope.launch {
            foodRepository.insertFood(food.copy(userId = currentUserId, date = todayDate))
            fetchFoods() // Fetch data after insertion
            triggerWidgetUpdate()
        }
    }

    fun deleteFood(food: Food) {
        viewModelScope.launch {
            foodRepository.deleteFood(food)
            fetchFoods() // Fetch data after deletion
            triggerWidgetUpdate()
        }
    }

    private fun triggerWidgetUpdate() {
        val workManager = WorkManager.getInstance(context)
        val workRequest = OneTimeWorkRequestBuilder<DailyUpdateWorker>().build()
        workManager.enqueue(workRequest)
    }




    fun fetchFoodsByDate(date: String, onResult: (List<Food>) -> Unit) {
        viewModelScope.launch {
            val foodsForDate = foodRepository.getFoodByUserAndDate(currentUserId, date).first()
            _foodList.value = foodsForDate ?: emptyList() // Handle null case
            onResult(foodsForDate ?: emptyList())
        }
    }


}
