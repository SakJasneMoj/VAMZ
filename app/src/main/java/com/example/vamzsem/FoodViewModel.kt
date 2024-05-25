package com.example.vamzsem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vamzsem.food_database.Food
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

class FoodViewModel(
    private val foodRepository: FoodRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _foodList = MutableStateFlow<List<Food>>(emptyList())
    val foodList: StateFlow<List<Food>> = _foodList.asStateFlow()

    private val _totalCalories = MutableStateFlow(0)
    val totalCalories: StateFlow<Int> = _totalCalories.asStateFlow()

    private var currentUserId: String = "" // Set this when a user logs in
    private val todayDate: Date = Calendar.getInstance().time

    init {
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
        }
    }

    fun deleteFood(food: Food) {
        viewModelScope.launch {
            foodRepository.deleteFood(food)
        }
    }

    fun setUserId(userId: String) {
        currentUserId = userId
        viewModelScope.launch {
            foodRepository.getFoodByUserAndDate(userId, todayDate).collect { foods ->
                _foodList.value = foods
                _totalCalories.value = foods.sumOf { it.calories }
            }
        }
    }
}
