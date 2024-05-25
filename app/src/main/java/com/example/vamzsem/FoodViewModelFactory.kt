package com.example.vamzsem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FoodViewModelFactory(
    private val foodRepository: FoodRepository,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FoodViewModel::class.java)) {
            return FoodViewModel(foodRepository, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
